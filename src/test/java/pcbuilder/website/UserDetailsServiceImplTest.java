package pcbuilder.website;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.repositories.UserDao;
import pcbuilder.website.services.UserService;
import pcbuilder.website.services.impl.UserDetailsServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserDao userDao;
    @Mock
    private UserService userService;
    @InjectMocks
    private UserDetailsServiceImpl service;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("secret");
    }
    
    /*@Test
    void shouldLoadUserByUsername() {
        when(userDao.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        UserDetails userDetails = service.loadUserByUsername("test@example.com");
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("secret", userDetails.getPassword());
    }*/

    @Test
    void shouldThrowWhenUserNotFound() {
        when(userDao.findByEmail("notfound@example.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.loadUserByUsername("notfound@example.com"));
    }
}
