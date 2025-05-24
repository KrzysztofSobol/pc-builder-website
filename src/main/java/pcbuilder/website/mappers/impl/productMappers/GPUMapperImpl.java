package pcbuilder.website.mappers.impl.productMappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.GPUDto;
import pcbuilder.website.models.entities.products.GPU;

@Component
public class GPUMapperImpl implements Mapper<GPU, GPUDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public GPUDto mapTo(GPU gpu) {
        return modelMapper.map(gpu, GPUDto.class);
    }

    @Override
    public GPU mapFrom(GPUDto gpuDto) {
        return modelMapper.map(gpuDto, GPU.class);
    }
}
