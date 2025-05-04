package pcbuilder.website.services;

import org.springframework.stereotype.Component;
import pcbuilder.website.models.entities.User;

import java.util.List;

@Component
public interface UserService {
    User save(User user);
    List<User> findAll();
}
