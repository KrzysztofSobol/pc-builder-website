package pcbuilder.website.models.entities.products;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "GPUs")
@PrimaryKeyJoinColumn(name = "productID")
@DiscriminatorValue("GPU")
public class GPU extends Product {
    private String chipset;
    private Integer memory;
    private Integer coreClock;
    private Integer boostClock;
    private String color;
    private Integer length; // (mm)
}
