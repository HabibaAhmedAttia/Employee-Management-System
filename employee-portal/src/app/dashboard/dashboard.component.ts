import { Component, OnInit } from '@angular/core';
import { EmployeeService, Employee } from '../services/employee.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { AuthService } from '../auth/auth.service';
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
    private router: Router,
    private auth: AuthService, 
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
  exportToExcel() {
  this.employeeService.getAllEmployeesWithoutPagination().subscribe(res => {
    if (res.success) {
      const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(res.data);
      const wb: XLSX.WorkBook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, 'Employees');

      const excelBuffer: any = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
      const blob: Blob = new Blob([excelBuffer], { type: 'application/octet-stream' });
      saveAs(blob, 'employees.xlsx');
    }
  });
}
  logout() {
    this.auth.clearToken();
    this.router.navigate(['/login']);
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
