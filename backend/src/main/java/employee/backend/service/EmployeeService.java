package employee.backend.service;

import employee.backend.dto.EmployeeRequest;
import employee.backend.dto.EmployeeResponse;
import employee.backend.entity.Employee;
import employee.backend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeResponse create(EmployeeRequest req) {
        Employee emp = new Employee();
        emp.setFirstName(req.getFirstName());
        emp.setLastName(req.getLastName());
        emp.setEmail(req.getEmail());
        emp.setDepartment(req.getDepartment());
        emp.setSalary(req.getSalary());
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists: " + req.getEmail());
        }
        Employee saved = repo.save(emp);
        return mapToResponse(saved);
    }

    public List<EmployeeResponse> getAll() {
        return repo.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse getById(Long id) {
        return repo.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public EmployeeResponse update(Long id, EmployeeRequest req) {
        Employee existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        existing.setFirstName(req.getFirstName());
        existing.setLastName(req.getLastName());
        existing.setEmail(req.getEmail());
        existing.setDepartment(req.getDepartment());
        existing.setSalary(req.getSalary());
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists: " + req.getEmail());
        }
        Employee saved = repo.save(existing);
        return mapToResponse(saved);
    }

    public void delete(Long id) {
        Employee existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        repo.delete(existing);
    }

    private EmployeeResponse mapToResponse(Employee emp) {
        return new EmployeeResponse(
                emp.getId(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getEmail(),
                emp.getDepartment(),
                emp.getSalary(),
                emp.getJoinedDate()
        );
    }
}