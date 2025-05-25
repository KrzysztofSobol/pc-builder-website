package pcbuilder.website.services.impl;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;
import pcbuilder.website.enums.OrderStatus;
import pcbuilder.website.models.dto.payment.PaymentRequestDto;
import pcbuilder.website.models.dto.payment.PaymentResponseDto;
import pcbuilder.website.models.entities.Order;
import pcbuilder.website.models.entities.OrderItem;
import pcbuilder.website.models.entities.Product;
import pcbuilder.website.services.OrderService;
import pcbuilder.website.services.PaymentService;
import pcbuilder.website.utils.StripeConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final OrderService orderService;
    private final StripeConfig stripeConfig;

    public PaymentServiceImpl(OrderService orderService, StripeConfig stripeConfig) {
        this.orderService = orderService;
        this.stripeConfig = stripeConfig;
    }

    @Override
    public PaymentResponseDto createCheckoutSession(PaymentRequestDto paymentRequest) {
        try {
            Order order = orderService.findById(paymentRequest.getOrderId())
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            if(order.getStatus() != OrderStatus.PENDING_PAYMENT) {
                throw new RuntimeException("Order is not PENDING_PAYMENT state!!!");
            }

            // stripe items for checkout
            List<SessionCreateParams.LineItem> lineItems = createLineItems(order);

            // checkout session
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(stripeConfig.getSuccessUrl() + "?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(stripeConfig.getCancelUrl())
                    .addAllLineItem(lineItems)
                    .putMetadata("order_id", order.getOrderID().toString())
                    .setCustomerEmail(order.getUser().getEmail())
                    .build();

            Session session = Session.create(params);

            orderService.update(order);

            return PaymentResponseDto.builder()
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .publicKey(stripeConfig.getPublicKey())
                    .build();

        } catch (StripeException e) {
            throw new RuntimeException("Failed to create payment session: " + e.getMessage());
        }
    }

    private List<SessionCreateParams.LineItem> createLineItems(Order order) {
        return order.getOrderItems().stream()
                .map(item -> SessionCreateParams.LineItem.builder()
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(item.getProduct().getName())
                                        .setDescription(item.getProduct().getDescription())
                                        .build())
                                .setUnitAmount(Math.round(item.getProduct().getPrice() * 100)) // convert to cents
                                .build())
                        .setQuantity(item.getQuantity().longValue())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void handleSuccessfulPayment(String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);
            String orderId = session.getMetadata().get("order_id");

            Order order = orderService.findById(Long.parseLong(orderId))
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // update order status
            order.setStatus(OrderStatus.PAYMENT_SUCCEEDED);
            order.setStripePaymentIntentId(session.getPaymentIntent());
            order.setPaymentDate(LocalDateTime.now());

            orderService.update(order);

        } catch (StripeException e) {
            throw new RuntimeException("Failed to process successful payment");
        }
    }

    @Override
    public void handleFailedPayment(String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);
            String orderId = session.getMetadata().get("order_id");

            Order order = orderService.findById(Long.parseLong(orderId))
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // update order status
            order.setStatus(OrderStatus.PAYMENT_FAILED);
            orderService.update(order);

            // Restore product stock
            restoreProductStock(order);
        } catch (StripeException e) {
        }
    }

    private void restoreProductStock(Order order) {
        for(OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
        }
        // TODO
    }

    @Override
    public boolean verifyWebhookSignature(String payload, String sigHeader) {
        try {
            Webhook.constructEvent(payload, sigHeader, stripeConfig.getWebhookSecret());
            return true;
        } catch (SignatureVerificationException e) {
            return false;
        }
    }
}
