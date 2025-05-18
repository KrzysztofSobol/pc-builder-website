package pcbuilder.website.models.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {
    private Long productID;
    private String productName;
    private String imageUrl;
    private Double unitPrice;
    private Integer quantity;
    private Double subtotal;
}