package pcbuilder.website.models.dto.payment;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentResponseDto {
    private String sessionId;
    private String sessionUrl;
    private String publicKey;
}
