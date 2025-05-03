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
@Table(name = "CPUCoolers")
@PrimaryKeyJoinColumn(name = "productID")
@DiscriminatorValue("CPUCooler")
public class CPUCooler extends Product {
    private Integer minRPM;
    private Integer maxRPM;
    private Integer minNoiseLevel;
    private Integer maxNoiseLevel;
    private String color;
    private Integer height;
}
