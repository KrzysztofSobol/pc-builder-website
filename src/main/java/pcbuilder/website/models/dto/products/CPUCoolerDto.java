package pcbuilder.website.models.dto.products;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pcbuilder.website.models.dto.ProductDto;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CPUCoolerDto extends ProductDto {
    private Integer minRPM;
    private Integer maxRPM;
    private Integer minNoiseLevel;
    private Integer maxNoiseLevel;
    private String color;
    private Integer height;
}
