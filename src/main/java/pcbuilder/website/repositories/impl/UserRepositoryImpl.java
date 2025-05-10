package pcbuilder.website.repositories.impl;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.repositories.UserRepository;

import java.util.UUID;

@Repository
public class UserRepositoryImpl extends GenericDaoImpl<User, UUID> implements UserRepository {

}
