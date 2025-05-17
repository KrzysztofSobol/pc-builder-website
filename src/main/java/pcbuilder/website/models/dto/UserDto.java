package pcbuilder.website.models.dto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDto {
    private UUID userID;
    private String username;
    private String email;
    private LocalDateTime createdAt;
}
