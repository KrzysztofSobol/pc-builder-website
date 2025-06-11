package pcbuilder.website.models.dto;
import lombok.Data;
import pcbuilder.website.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDto {
    private UUID userID;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private Role role;
}
