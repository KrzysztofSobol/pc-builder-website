package pcbuilder.website.models.dto.products;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pcbuilder.website.enums.PanelType;
import pcbuilder.website.models.dto.ProductDto;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CaseDto extends ProductDto {
    private String type;
    private String color;
    private PanelType sidePanel;
}
