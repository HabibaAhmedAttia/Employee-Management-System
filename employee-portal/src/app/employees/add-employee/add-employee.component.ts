import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeService, Employee } from '../../services/employee.service';
import { FormsModule } from '@angular/forms';  
import { CommonModule } from '@angular/common'; 

@Component({
  selector: 'app-add-employee',
  standalone: true, 
  imports: [CommonModule, FormsModule], 
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css'],
})
export class AddEmployeeComponent {
  employee: Employee = {
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    department: '',
    salary: 0,
    joinedDate: ''
  };

  constructor(private employeeService: EmployeeService, private router: Router) {}

  onSubmit() {
    this.employeeService.addEmployee(this.employee).subscribe(res => {
      if (res.success) {
        alert('Employee added successfully!');
        this.router.navigate(['/dashboard']); 
      }
    });
  }

  cancel() {
    this.router.navigate(['/dashboard']);
  }
}
