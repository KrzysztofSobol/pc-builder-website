package pcbuilder.website.models.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecipientDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long recipientID;
    private String fullName;
    private String email;
    private String zipCode;
    private String city;
    private String phoneNumber;
}
