package pcbuilder.website.mappers.impl.productMappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.RAMDto;
import pcbuilder.website.models.entities.products.RAM;

@Component
public class RAMMapperImpl implements Mapper<RAM, RAMDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public RAMDto mapTo(RAM ram) {
        return modelMapper.map(ram, RAMDto.class);
    }

    @Override
    public RAM mapFrom(RAMDto ramDto) {
        return modelMapper.map(ramDto, RAM.class);
    }
}
