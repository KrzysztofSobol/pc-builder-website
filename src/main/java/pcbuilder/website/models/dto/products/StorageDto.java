package pcbuilder.website.models.dto.products;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pcbuilder.website.enums.StorageType;
import pcbuilder.website.models.dto.ProductDto;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StorageDto extends ProductDto {
    private Integer capacity;
    private StorageType type;
    private Integer cache;
    private String formFactor;
    private String interFace;
}
