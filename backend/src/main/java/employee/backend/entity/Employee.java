package employee.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String department;
    @Column(nullable = false)
    private Double salary;
    @Column(updatable = false)
    private LocalDate joinedDate;
    @PrePersist
    public void prePersist() {
        this.joinedDate = LocalDate.now().withDayOfMonth(1);
    }
}
