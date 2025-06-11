package pcbuilder.website.mappers.impl.productMappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.PSUDto;
import pcbuilder.website.models.entities.products.PSU;

@Component
public class PSUMapperImpl implements Mapper<PSU, PSUDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public PSUDto mapTo(PSU psu) {
        return modelMapper.map(psu, PSUDto.class);
    }

    @Override
    public PSU mapFrom(PSUDto psuDto) {
        return modelMapper.map(psuDto, PSU.class);
    }
}
