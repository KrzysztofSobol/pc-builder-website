package pcbuilder.website.repositories.impl;

import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.repositories.UserDao;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, UUID> implements UserDao {
    @Override
    @Transactional
    public Optional<User> findByUsername(String username) {
        // zwalidowac pan
        TypedQuery<User> query = em.createNamedQuery("User.findByUsername", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst();
    }
}
