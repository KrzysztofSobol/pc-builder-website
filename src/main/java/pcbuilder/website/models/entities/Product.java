package pcbuilder.website.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "Products")
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
