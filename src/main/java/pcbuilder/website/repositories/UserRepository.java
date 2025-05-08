package pcbuilder.website.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.User;

import java.util.UUID;

@Repository // TAK, NIE ROBIMY !!!!!
public interface UserRepository extends CrudRepository<User, UUID> {

}
