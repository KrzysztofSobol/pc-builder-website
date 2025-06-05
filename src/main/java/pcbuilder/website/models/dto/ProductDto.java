package pcbuilder.website.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductDto {
    private Long productID;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private Long stock;
}
