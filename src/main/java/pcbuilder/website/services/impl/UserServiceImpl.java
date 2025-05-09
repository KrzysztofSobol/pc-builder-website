package pcbuilder.website.services.impl;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.repositories.UserRepository;
import pcbuilder.website.services.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll(){
        return null;
    }
}
