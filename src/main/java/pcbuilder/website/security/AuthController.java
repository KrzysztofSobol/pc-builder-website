package pcbuilder.website.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
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
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final Mapper<User, UserDto> userMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, UserService userService, Mapper<User, UserDto> userMapper, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );

            final User user = userService.findByEmail(authRequest.getEmail()).get();
            final String token = jwtUtil.generateToken(user);

            Cookie jwtCookie = new Cookie("auth_token", token);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(86400);
            jwtCookie.setHttpOnly(true);
            //jwtCookie.setSecure(true); // this sends only through "https", swagger is "http" so no use for it for now
            response.addCookie(jwtCookie);

            return ResponseEntity.ok(new AuthResponse(token, userMapper.mapTo(user)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Nieprawidłowy email lub hasło."));
        }
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
                .password(passwordEncoder.encode(registerRequest.getPassword()))
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

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("auth_token", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.noContent().build();
    }
}
