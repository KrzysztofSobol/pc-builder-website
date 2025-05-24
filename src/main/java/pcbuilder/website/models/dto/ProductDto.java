package pcbuilder.website.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long productID;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private Long stock;
}
