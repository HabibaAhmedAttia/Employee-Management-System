package employee.backend.controller;

import employee.backend.dto.EmployeeRequest;
import employee.backend.dto.EmployeeResponse;
import employee.backend.exception.ApiResponse;
import employee.backend.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Employee", description = "Endpoints for managing employees")
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    private final EmployeeService service;
    @Operation(summary = "Create a new employee", description = "Adds a new employee to the database. Email must be unique.")
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> create(@Valid @RequestBody EmployeeRequest employee) {
        EmployeeResponse response = service.create(employee);
        return ResponseEntity.ok(ApiResponse.success("Employee created successfully", response));
    }
    @Operation(summary = "Get all employees", description = "Retrieve a paginated list of employees. Use `page` query param to navigate pages.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAll(
            @RequestParam(value = "page", required = false) Integer page) {
        List<EmployeeResponse> employees = service.getAll(page);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully", employees));
    }
    @Operation(summary = "Get an employee by ID", description = "Retrieve a single employee using their unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getById(@PathVariable Long id) {
        EmployeeResponse employee = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Employee retrieved successfully", employee));
    }
    @Operation(summary = "Update an employee", description = "Update employee details by their ID. Only provided fields will be updated.")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> update(
            @PathVariable Long id,
            @RequestBody EmployeeRequest employee) {
        EmployeeResponse updatedEmployee = service.update(id, employee);
        return ResponseEntity.ok(ApiResponse.success("Employee updated successfully", updatedEmployee));
    }
    @Operation(summary = "Get all employees without pagination", description = "Retrieve all employees in the database at once, no paging applied.")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployeesWithoutPagination() {
        List<EmployeeResponse> employees = service.getAllWithoutPagination();
        return ResponseEntity.ok(ApiResponse.success("All employees retrieved successfully", employees));
    }
    @Operation(summary = "Delete an employee", description = "Delete an employee by their ID. Employee data will be removed permanently.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deleted successfully", null));
    }
}
