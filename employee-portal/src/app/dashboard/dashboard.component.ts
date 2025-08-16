import { Component, OnInit } from '@angular/core';
import { EmployeeService, Employee } from '../services/employee.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],   
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  employees: Employee[] = [];
  page = 1;

  constructor(private employeeService: EmployeeService,
    private router: Router   // ⬅️ هنا بنـ Inject الـ Router
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees() {
    this.employeeService.getEmployees(this.page).subscribe(res => {
      if (res.success) {
        this.employees = res.data;
      }
    });
  }

  nextPage() {
    this.page++;
    this.loadEmployees();
  }

  prevPage() {
    if (this.page > 1) {
      this.page--;
      this.loadEmployees();
    }
  }

  addEmployee() {
  this.router.navigate(['/employees/add']);
}


  updateEmployee(id: number) {
  this.router.navigate(['/employees/edit', id]);
}


  deleteEmployee(id: number) {
    if (confirm("Are you sure you want to delete this employee?")) {
      this.employeeService.deleteEmployee(id).subscribe(() => {
        this.loadEmployees(); // refresh list
      });
    }
  }
}
