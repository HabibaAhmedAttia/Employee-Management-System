package employee.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response containing user details")
public class UserResponse {
    @Schema(description = "User ID", example = "1")
    private Long id;
    @Schema(description = "Username", example = "habiba123")
    private String username;
    @Schema(description = "User email", example = "habiba@example.com")
    private String email;
}