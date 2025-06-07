package pcbuilder.website.mappers.impl.productMappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.MotherboardDto;
import pcbuilder.website.models.entities.products.Motherboard;

@Component
public class MotherboardMapperImpl implements Mapper<Motherboard, MotherboardDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public MotherboardDto mapTo(Motherboard motherboard) {
        return modelMapper.map(motherboard, MotherboardDto.class);
    }

    @Override
    public Motherboard mapFrom(MotherboardDto motherboardDto) {
        return modelMapper.map(motherboardDto, Motherboard.class);
    }
}
