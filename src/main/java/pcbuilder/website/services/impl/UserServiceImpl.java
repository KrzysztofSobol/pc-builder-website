package pcbuilder.website.services.impl;

import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.repositories.UserDao;
import pcbuilder.website.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger log =
            Logger.getLogger(UserServiceImpl.class.getName());
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        try {
            log.finer("Initializing User Service...");
            this.userDao = userDao;
        } catch (Exception e) {
            log.severe("Failed to initialize User Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public User save(User user) {
        try {
            log.fine("Saving user...");
            return userDao.save(user);
        } catch (Exception e) {
            log.severe("Failed to save user error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try {
            log.fine("Deleting user...");
            userDao.delete(user);
        } catch (Exception e) {
            log.severe("Failed to delete user error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(User user) {
        try {
            log.fine("Updating user...");
            return userDao.update(user);
        } catch (Exception e) {
            log.severe("Failed to update user error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public User partialUpdate(UUID id, User user) {
        try {
            log.fine("Partial update of user...");
            user.setUserID(id);

            return userDao.findById(id).map(existingUser -> {
                Optional.ofNullable(user.getUsername()).ifPresent(existingUser::setUsername);
                Optional.ofNullable(user.getEmail()).ifPresent(existingUser::setEmail);
                Optional.ofNullable(user.getPassword()).ifPresent(existingUser::setPassword);
                Optional.ofNullable(user.getRole()).ifPresent(existingUser::setRole);
                return userDao.update(existingUser);
            }).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (RuntimeException e) {
            log.warning("Failed to partial update user error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(UUID id) {
        try {
            log.fine("Finding user...");
            return userDao.findById(id);
        } catch (Exception e) {
            log.severe("Failed to find user error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            log.fine("Finding user by username...");
            return userDao.findByUsername(username);
        } catch (Exception e) {
            log.severe("Failed to find user by username error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            log.fine("Finding user by email...");
            return userDao.findByEmail(email);
        } catch (Exception e) {
            log.severe("Failed to find user by email error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll(){
        try {
            log.fine("Finding all users...");
            return userDao.findAll();
        } catch (Exception e) {
            log.severe("Failed to find all users error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(UUID id) {
        try {
            log.fine("Checking if user exists...");
            return userDao.exists(id);
        } catch (Exception e) {
            log.severe("Failed to check if user exists error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
