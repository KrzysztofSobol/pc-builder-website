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
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final static Logger log =
            Logger.getLogger(PaymentServiceImpl.class.getName());
    private final OrderService orderService;
    private final StripeConfig stripeConfig;

    public PaymentServiceImpl(OrderService orderService, StripeConfig stripeConfig) {
        try {
            log.finer("Initializing Payment Service...");
            this.orderService = orderService;
            this.stripeConfig = stripeConfig;
        } catch (Exception e) {
            log.severe("Failed to initialize Payment Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public PaymentResponseDto createCheckoutSession(PaymentRequestDto paymentRequest) {
        try {
            log.fine("Creating checkout session...");
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
            log.severe("Failed to create payment session: " + e.getMessage());
            throw new RuntimeException("Failed to create payment session: " + e.getMessage());
        }
    }

    private List<SessionCreateParams.LineItem> createLineItems(Order order) {
        try {
            log.fine("Creating line items...");
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
        } catch (Exception e) {
            log.severe("Failed to create line items: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleSuccessfulPayment(String sessionId) {
        try {
            log.fine("Processing successful payment...");
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
            log.warning("Failed to process successful payment: " + e.getMessage());
            throw new RuntimeException("Failed to process successful payment");
        }
    }

    @Override
    public void handleFailedPayment(String sessionId) {
        try {
            log.fine("Processing failed payment...");
            Session session = Session.retrieve(sessionId);
            String orderId = session.getMetadata().get("order_id");

            Order order = orderService.findById(Long.parseLong(orderId))
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // update order status
            order.setStatus(OrderStatus.PAYMENT_FAILED);
            orderService.update(order);

            // Restore product.ts stock
            restoreProductStock(order);
        } catch (StripeException e) {
            log.warning("Failed to process failed payment: " + e.getMessage());
            throw new RuntimeException("Failed to process failed payment");
        }
    }

    private void restoreProductStock(Order order) {
        try {
            log.fine("Restoring product stock...");
            for(OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
            }
        } catch (Exception e) {
            log.severe("Failed to restore product stock: " + e.getMessage());
            throw new RuntimeException(e);
        }
        // TODO
    }

    @Override
    public boolean verifyWebhookSignature(String payload, String sigHeader) {
        try {
            log.fine("Verifying webhook signature...");
            Webhook.constructEvent(payload, sigHeader, stripeConfig.getWebhookSecret());
            return true;
        } catch (SignatureVerificationException e) {
            log.warning("Failed to verify webhook signature: " + e.getMessage());
            return false;
        }
    }
}
