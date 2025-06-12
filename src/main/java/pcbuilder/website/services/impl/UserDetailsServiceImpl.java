package pcbuilder.website.services.impl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.services.UserService;

import java.util.logging.Logger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final static Logger log =
            Logger.getLogger(UserDetailsServiceImpl.class.getName());
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        try {
            log.finer("Initializing User Details Service...");
            this.userService = userService;
        } catch (Exception e) {
            log.severe("Failed to initialize User Details Service error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            log.fine("Loading user by username...");
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        } catch (UsernameNotFoundException e) {
            log.warning("Failed to load user by username error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}