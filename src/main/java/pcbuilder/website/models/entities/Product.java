package pcbuilder.website.models.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "Products")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "productType")
public class Product {
    @Id
    @GeneratedValue
    private Long productID;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private Long quantity;
}
