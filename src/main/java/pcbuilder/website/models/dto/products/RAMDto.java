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
public class RAMDto extends ProductDto {
    private Integer ddrGen;
    private Integer speed;
    private Integer moduleCount;
    private Integer totalCapacity;
    private String color;
    private Integer firstWordLatency;
}
