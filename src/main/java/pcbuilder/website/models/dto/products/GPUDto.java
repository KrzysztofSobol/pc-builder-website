package pcbuilder.website.models.dto.products;

import pcbuilder.website.models.dto.ProductDto;

public class GPUDto extends ProductDto {
    private String chipset;
    private Integer memory;
    private Integer coreClock;
    private Integer boostClock;
    private String color;
    private Integer length; // (mm)
}
