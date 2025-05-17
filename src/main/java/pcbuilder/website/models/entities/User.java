package pcbuilder.website.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pcbuilder.website.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@NamedQuery(
        name = "User.findByUsername",
        query = "SELECT u FROM User u WHERE u.username = :username"
)
@NamedQuery(
        name = "User.findByEmail",
        query = "SELECT u FROM User u WHERE u.email = :email"
)

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue
    private UUID userID;
    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;
}
