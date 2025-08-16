package employee.backend.service;

import employee.backend.dto.EmployeeRequest;
import employee.backend.dto.EmployeeResponse;
import employee.backend.entity.Employee;
import employee.backend.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<EmployeeResponse> getAll(Integer page) {

        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Employee> employeePage = repo.findAll(pageable);
        return employeePage.stream()
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
        if (req.getFirstName() != null) existing.setFirstName(req.getFirstName());
        if (req.getLastName() != null) existing.setLastName(req.getLastName());
        if (req.getEmail() != null) {
            if (repo.existsByEmail(req.getEmail()) && !req.getEmail().equals(existing.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            existing.setEmail(req.getEmail());
        }
        if (req.getDepartment() != null) existing.setDepartment(req.getDepartment());
        if (req.getSalary() != null) existing.setSalary(req.getSalary());

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