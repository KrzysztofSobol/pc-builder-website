package pcbuilder.website.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pcbuilder.website.models.entities.User;
import pcbuilder.website.services.UserService;
import pcbuilder.website.utils.JwtUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final static Logger log =
            Logger.getLogger(JwtRequestFilter.class.getName());
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(UserService userService, JwtUtil jwtUtil) {
        try {
            log.info("JwtRequestFilter created.");
            this.userService = userService;
            this.jwtUtil = jwtUtil;
        } catch (Exception e) {
            log.severe("JwtRequestFilter creation failed.");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String email = null;
        String token = null;

        log.info("Request received. " + request.getRequestURI());
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null) {
            try {
                email = jwtUtil.extractEmail(token);
            } catch (Exception e) {
                log.severe("Error while extracting email from token.");
                // error handle that or log idk
            }
        }

        if(email != null){
            Optional<User> user = userService.findByEmail(email);
            User userEntity = user.get();

            boolean isTokenValid = jwtUtil.validateToken(token, userEntity);

            if(isTokenValid){
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name()));
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEntity, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
