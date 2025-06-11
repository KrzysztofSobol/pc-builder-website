package pcbuilder.website.mappers.impl.productMappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.CaseDto;
import pcbuilder.website.models.entities.products.Case;

@Component
public class CaseMapperImpl implements Mapper<Case, CaseDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CaseDto mapTo(Case c) {
        return modelMapper.map(c, CaseDto.class);
    }

    @Override
    public Case mapFrom(CaseDto dto) {
        return modelMapper.map(dto, Case.class);
    }
}
