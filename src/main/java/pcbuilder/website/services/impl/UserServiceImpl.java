package pcbuilder.website.services.impl;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.repositories.UserDao;
import pcbuilder.website.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public User partialUpdate(UUID id, User user) {
        user.setUserID(id);

        return userDao.findById(id).map(existingUser -> {
            Optional.ofNullable(user.getUsername()).ifPresent(existingUser::setUsername);
            Optional.ofNullable(user.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(user.getPassword()).ifPresent(existingUser::setPassword);
            Optional.ofNullable(user.getRole()).ifPresent(existingUser::setRole);
            return userDao.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll(){
        return userDao.findAll();
    }

    @Override
    public boolean exists(UUID id) {
        return userDao.exists(id);
    }
}
