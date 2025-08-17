package employee.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotBlank(message = "Email is required")
    @Schema(description = "User email", example = "habiba@example.com")
    private String email;
    @NotBlank(message = "Password is required")
    @Schema(description = "User password", example = "P@ssw0rd123")
    private String password;
}
