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
@Table(name = "CPUs")
@PrimaryKeyJoinColumn(name = "productID")
@DiscriminatorValue("CPU")
public class CPU extends Product {
    private String socket;
    private Integer coreCount;
    private Double coreClock;
    private Double boostClock;
    private Integer tdp;
    private Boolean graphics;
    private Boolean smt;
}
