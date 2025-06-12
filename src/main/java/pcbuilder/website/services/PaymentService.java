package pcbuilder.website.services;

import pcbuilder.website.models.dto.payment.PaymentRequestDto;
import pcbuilder.website.models.dto.payment.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto createCheckoutSession(PaymentRequestDto paymentRequest);
    void handleSuccessfulPayment(String sessionId);
    void handleFailedPayment(String sessionId);
    boolean verifyWebhookSignature(String payload, String sigHeader);
    String getEmailFromSession(String sessionId);
}
