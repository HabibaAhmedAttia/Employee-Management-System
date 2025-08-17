package employee.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Request for user registration")
public class UserRegisterRequest {
    @NotBlank(message = "Username is required")
    @Schema(description = "Username", example = "habiba123")
    private String username;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "User email", example = "habiba@example.com")
    private String email;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "Password must contain at least 8 characters, including uppercase, lowercase, number, and special character")
    @Schema(description = "User password", example = "P@ssw0rd123")
    private String password;
}