package employee.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Schema(description = "Response returned for an employee")
public class EmployeeResponse {
    @Schema(description = "Employee ID", example = "1")
    private Long id;
    @Schema(description = "First name", example = "Habiba")
    private String firstName;
    @Schema(description = "Last name", example = "Ahmed")
    private String lastName;
    @Schema(description = "Email address", example = "habiba@example.com")
    private String email;
    @Schema(description = "Department name", example = "HR")
    private String department;
    @Schema(description = "Salary", example = "1200.0")
    private Double salary;
    @Schema(description = "Date when employee joined", example = "2025-08-17")
    private LocalDate joinedDate;
}