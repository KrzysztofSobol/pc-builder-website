package pcbuilder.website.models.entities.products;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pcbuilder.website.models.entities.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "Motherboards")
public class Motherboard extends Product {
    private String socket;
    private String formFactor;
    private Integer maxMemory;
    private Integer memorySlots;
    private String color;
}
