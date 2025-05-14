package pcbuilder.website.repositories.impl;

import org.springframework.stereotype.Repository;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.repositories.UserDao;

import java.util.UUID;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, UUID> implements UserDao {
}
