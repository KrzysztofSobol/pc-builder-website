package pcbuilder.website.mappers.impl.productMappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.CPUCoolerDto;
import pcbuilder.website.models.entities.products.CPUCooler;

@Component
public class CPUCoolerMapperImpl implements Mapper<CPUCooler, CPUCoolerDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CPUCoolerDto mapTo(CPUCooler cooler) {
        return modelMapper.map(cooler, CPUCoolerDto.class);
    }

    @Override
    public CPUCooler mapFrom(CPUCoolerDto dto) {
        return modelMapper.map(dto, CPUCooler.class);
    }
}
