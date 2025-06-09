package pcbuilder.website.mappers.impl.productMappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.products.StorageDto;
import pcbuilder.website.models.entities.products.Storage;

@Component
public class StorageMapperImpl implements Mapper<Storage, StorageDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public StorageDto mapTo(Storage storage) {
        return modelMapper.map(storage, StorageDto.class);
    }

    @Override
    public Storage mapFrom(StorageDto storageDto) {
        return modelMapper.map(storageDto, Storage.class);
    }
}
