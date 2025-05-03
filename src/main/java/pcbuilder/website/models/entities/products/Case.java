package pcbuilder.website.models.entities.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pcbuilder.website.enums.PanelType;
import pcbuilder.website.models.entities.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "Cases")
@PrimaryKeyJoinColumn(name = "productID")
@DiscriminatorValue("Case")
public class Case extends Product {
    private String type;
    private String color;
    @Enumerated(EnumType.STRING)
    private PanelType sidePanel;
}
