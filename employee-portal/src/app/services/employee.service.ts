import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  department: string;
  salary: number;
  joinedDate: string;
}

interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private baseUrl = 'http://localhost:8080/api/employees';

  constructor(private http: HttpClient) {}

  getEmployees(page: number = 1): Observable<ApiResponse<Employee[]>> {
    return this.http.get<ApiResponse<Employee[]>>(`${this.baseUrl}?page=${page}`);
  }

  getEmployeeById(id: number) {
  return this.http.get<ApiResponse<Employee>>(`${this.baseUrl}/${id}`);
}

updateEmployee(id: number, employee: Employee) {
  return this.http.put<ApiResponse<Employee>>(`${this.baseUrl}/${id}`, employee);
}


  addEmployee(emp: Employee) {
  return this.http.post<{ success: boolean, data: Employee }>(`${this.baseUrl}`, emp);
}
  getAllEmployeesWithoutPagination() {
  return this.http.get<{ success: boolean, data: Employee[] }>(`${this.baseUrl}/all`);
}


  deleteEmployee(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.baseUrl}/${id}`);
  }
}
