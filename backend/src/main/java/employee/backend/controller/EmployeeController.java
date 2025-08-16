package employee.backend.controller;

import employee.backend.dto.EmployeeRequest;
import employee.backend.dto.EmployeeResponse;
import employee.backend.entity.Employee;
import employee.backend.exception.ApiResponse;
import employee.backend.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> create(@Valid @RequestBody EmployeeRequest employee) {
        EmployeeResponse response = service.create(employee);
        return ResponseEntity.ok(ApiResponse.success("Employee created successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAll(
            @RequestParam(value = "page", required = false) Integer page) {
        List<EmployeeResponse> employees = service.getAll(page);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully", employees));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getById(@PathVariable Long id) {
        EmployeeResponse employee = service.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Employee retrieved successfully", employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> update(
            @PathVariable Long id,
            @RequestBody EmployeeRequest employee) {
        EmployeeResponse updatedEmployee = service.update(id, employee);
        return ResponseEntity.ok(ApiResponse.success("Employee updated successfully", updatedEmployee));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deleted successfully", null));
    }
}
