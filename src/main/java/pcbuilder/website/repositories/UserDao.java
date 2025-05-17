package pcbuilder.website.repositories;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDao extends GenericDao<User, UUID>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
