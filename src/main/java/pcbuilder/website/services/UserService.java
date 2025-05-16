package pcbuilder.website.services;

import org.springframework.stereotype.Component;
import pcbuilder.website.models.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User save(User user);
    void delete(User user);
    User update(User user);
    User partialUpdate(UUID id, User user);
    Optional<User> findById(UUID id);
    List<User> findAll();
    boolean exists(UUID id);
}