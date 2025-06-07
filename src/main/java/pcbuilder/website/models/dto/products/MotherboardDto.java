package pcbuilder.website.models.dto.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pcbuilder.website.models.dto.ProductDto;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class MotherboardDto extends ProductDto {
    private String socket;
    private String formFactor;
    private Integer maxMemory;
    private Integer memorySlots;
    private String color;
}
