package pcbuilder.website.models.dto.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long orderID;
    private UUID userID;
    private String username;
    private LocalDateTime orderDate;
    private List<OrderItemResponseDto> items;
    private Double totalPrice;
}
