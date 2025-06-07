package pcbuilder.website.mappers.impl.productMappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.CPUDto;
import pcbuilder.website.models.entities.products.CPU;

@Component
public class CPUMapperImpl implements Mapper<CPU, CPUDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CPUDto mapTo(CPU cpu) {
        return modelMapper.map(cpu, CPUDto.class);
    }

    @Override
    public CPU mapFrom(CPUDto cpuDto) {
        return modelMapper.map(cpuDto, CPU.class);
    }
}
