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

public class CPUDto extends ProductDto {
    private String socket;
    private Integer coreCount;
    private Double coreClock;
    private Double boostClock;
    private Integer tdp;
    private Boolean graphics;
    private Boolean smt;
}
