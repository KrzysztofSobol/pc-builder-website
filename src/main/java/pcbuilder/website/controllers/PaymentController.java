package pcbuilder.website.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pcbuilder.website.models.dto.payment.PaymentRequestDto;
import pcbuilder.website.models.dto.payment.PaymentResponseDto;
import pcbuilder.website.services.MailService;
import pcbuilder.website.services.PaymentService;

@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private final MailService mailService;

    public PaymentController(PaymentService paymentService, MailService mailService) {
        this.paymentService = paymentService;
        this.mailService = mailService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<PaymentResponseDto> createCheckoutSession(@RequestBody PaymentRequestDto paymentRequest) {
        try {
            PaymentResponseDto response = paymentService.createCheckoutSession(paymentRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        if (!paymentService.verifyWebhookSignature(payload, sigHeader)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode eventJson = mapper.readTree(payload);

            String eventType = eventJson.get("type").asText();

            switch (eventType) {
                case "checkout.session.completed":
                    String sessionId = eventJson.get("data").get("object").get("id").asText();

                    mailService.sendEmail(paymentService.getEmailFromSession(sessionId),"Payment confirmation", "Your payment has been successful!");

                    paymentService.handleSuccessfulPayment(sessionId);
                    break;

                case "checkout.session.expired":
                    String expiredSessionId = eventJson.get("data").get("object").get("id").asText();
                    mailService.sendEmail(paymentService.getEmailFromSession(expiredSessionId),"Payment failed", "Your payment has been failed!");
                    paymentService.handleFailedPayment(expiredSessionId);
                    break;
            }

            return ResponseEntity.ok("Success");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook error");
        }
    }
}
