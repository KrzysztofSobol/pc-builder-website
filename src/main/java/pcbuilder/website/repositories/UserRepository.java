package pcbuilder.website.repositories;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.User;

import java.util.List;

@Repository
public interface UserRepository {
    List<User> findAll();
}
