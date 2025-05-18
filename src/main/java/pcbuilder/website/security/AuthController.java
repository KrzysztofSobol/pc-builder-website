package pcbuilder.website.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pcbuilder.website.enums.Role;
import pcbuilder.website.mappers.Mapper;
import pcbuilder.website.models.dto.auth.AuthRequest;
import pcbuilder.website.models.dto.auth.AuthResponse;
import pcbuilder.website.models.dto.auth.RegisterRequest;
import pcbuilder.website.models.dto.UserDto;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.services.UserService;
import pcbuilder.website.utils.JwtUtil;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final Mapper<User, UserDto> userMapper;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtUtil jwtUtil, UserService userService, Mapper<User, UserDto> userMapper, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        final User user = userService.findByEmail(authRequest.getEmail()).get();
        final String token = jwtUtil.generateToken(user);

        Cookie jwtCookie = new Cookie("auth_token", token);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(86400);
        jwtCookie.setHttpOnly(true);
        //jwtCookie.setSecure(true); // this sends only through "https", swagger is "http" so no use for it for now
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(new AuthResponse(token, userMapper.mapTo(user)));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        if(userService.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already in use!"));
        }

        if(userService.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already in use!"));
        }

        if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Passwords do not match!"));
        }

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .role(Role.Customer)
                .createdAt(LocalDateTime.now())
                .build();

        final User savedUser = userService.save(user);
        final String token = jwtUtil.generateToken(savedUser);

        Cookie jwtCookie = new Cookie("auth_token", token);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(86400);
        jwtCookie.setHttpOnly(true);
        //jwtCookie.setSecure(true); // this sends only through "https", swagger is "http" so no use for it for now
        response.addCookie(jwtCookie);

        return new ResponseEntity<>(new AuthResponse(token, userMapper.mapTo(savedUser)), HttpStatus.OK);
    }
}
