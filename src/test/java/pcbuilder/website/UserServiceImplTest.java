package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.repositories.UserDao;
import pcbuilder.website.services.impl.UserServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl service;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("test@example.com");
    }

    @Test
    void shouldSaveUser() {
        when(userDao.save(user)).thenReturn(user);
        assertEquals(user, service.save(user));
    }

    @Test
    void shouldFindByEmail() {
        when(userDao.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        Optional<User> result = service.findByEmail("test@example.com");
        assertTrue(result.isPresent());
    }

    @Test
    void shouldDeleteUser() {
        doNothing().when(userDao).delete(user);
        assertDoesNotThrow(() -> service.delete(user));
    }
}
