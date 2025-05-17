package pcbuilder.website.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.UserDto;
import pcbuilder.website.models.entities.User;

@Component
public class UserMapperImpl implements Mapper<User, UserDto> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserDto mapTo(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
