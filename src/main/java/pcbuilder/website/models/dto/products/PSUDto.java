package pcbuilder.website.models.dto.products;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pcbuilder.website.enums.Efficiency;
import pcbuilder.website.enums.ModularType;
import pcbuilder.website.enums.PSUType;
import pcbuilder.website.models.dto.ProductDto;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PSUDto extends ProductDto {
    private PSUType type;
    private Efficiency efficiency;
    private Integer wattage;
    private ModularType modular;
    private String color;
}
