package pcbuilder.website.models.entities.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pcbuilder.website.enums.Efficiency;
import pcbuilder.website.enums.ModularType;
import pcbuilder.website.enums.PSUType;
import pcbuilder.website.models.entities.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "PSUs")
@PrimaryKeyJoinColumn(name = "productID")
@DiscriminatorValue("PSU")
public class PSU extends Product {
    @Enumerated(EnumType.STRING)
    private PSUType type;
    @Enumerated(EnumType.STRING)
    private Efficiency efficiency;
    private Integer wattage;
    @Enumerated(EnumType.STRING)
    private ModularType modular;
    private String color;
}
