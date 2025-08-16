import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService, Employee } from '../../services/employee.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-update-employee',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css'],
})
export class UpdateEmployeeComponent implements OnInit {
  employee: Employee = {
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    department: '',
    salary: 0,
    joinedDate: ''
  };

  constructor(
    private route: ActivatedRoute,
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  ngOnInit(): void {
  const id = +this.route.snapshot.paramMap.get('id')!;
  this.employeeService.getEmployeeById(id).subscribe(res => {
    if (res.success) {
      this.employee = res.data;
    }
  });
}


  onSubmit() {
    this.employeeService.updateEmployee(this.employee.id, this.employee).subscribe(res => {
      if (res.success) {
        alert('Employee updated successfully!');
        this.router.navigate(['/dashboard']);
      }
    });
  }

  cancel() {
    this.router.navigate(['/dashboard']);
  }
}
