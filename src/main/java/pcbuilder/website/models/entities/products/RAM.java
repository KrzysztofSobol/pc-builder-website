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
@Table(name = "RAM")
@PrimaryKeyJoinColumn(name = "productID")
@DiscriminatorValue("RAM")
public class RAM extends Product {
    private Integer ddrGen;
    private Integer speed;
    private Integer moduleCount;
    private Integer totalCapacity;
    private String color;
    private Integer firstWordLatency;
}
