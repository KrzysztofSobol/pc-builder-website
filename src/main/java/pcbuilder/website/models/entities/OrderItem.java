package pcbuilder.website.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "OrderItems")
public class OrderItem {
    @Id
    @GeneratedValue
    private Long orderItemID;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;
    private Integer quantity;
}
