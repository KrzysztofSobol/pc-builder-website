package pcbuilder.website.models.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pcbuilder.website.enums.ShipmentMethod;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private UUID userID;
    private RecipientDto recipient;
    private ShipmentMethod shipmentMethod;
    private List<OrderItemDto> products;
}