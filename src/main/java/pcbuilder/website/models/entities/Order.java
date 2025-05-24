package pcbuilder.website.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pcbuilder.website.enums.OrderStatus;
import pcbuilder.website.enums.ShipmentMethod;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "Orders")
public class Order {
    @Id  @GeneratedValue
    private Long orderID;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)  @Column(nullable = false)  @Builder.Default
    private OrderStatus status = OrderStatus.PENDING_PAYMENT;

    @Enumerated(EnumType.STRING)
    private ShipmentMethod shipmentMethod;

    @OneToOne(cascade = CascadeType.ALL)
    private Recipient recipient;
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
