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
import pcbuilder.website.enums.StorageType;
import pcbuilder.website.models.entities.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "Storages")
public class Storage extends Product {
    private Integer capacity;
    @Enumerated(EnumType.STRING)
    private StorageType type;
    private Integer cache;
    private String formFactor;
    private String interFace;
}
