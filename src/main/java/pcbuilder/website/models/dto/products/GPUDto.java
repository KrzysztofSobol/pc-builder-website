package pcbuilder.website.models.dto.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pcbuilder.website.models.dto.ProductDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class GPUDto extends ProductDto {
    private String chipset;
    private Integer memory;
    private Integer coreClock;
    private Integer boostClock;
    private String color;
    private Integer length; // (mm)
}
