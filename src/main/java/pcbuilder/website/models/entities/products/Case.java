package pcbuilder.website.models.entities.products;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
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
public class Case extends Product {
    private String type;
    private String color;
    @Enumerated(EnumType.STRING)
    private PanelType sidePanel;
}
