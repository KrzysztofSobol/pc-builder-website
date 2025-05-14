package pcbuilder.website.repositories;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.User;

import java.util.UUID;

@Repository
public interface UserDao extends GenericDao<User, UUID>{

}
