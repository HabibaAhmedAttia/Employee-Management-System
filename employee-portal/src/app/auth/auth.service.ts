import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

interface ApiResponse<T = any> {
  success: boolean;
  message: string;
  data: T;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private base = 'http://localhost:8080/api/auth';


  constructor(private http: HttpClient) {}

  register(payload: { username: string; email: string; password: string }): Observable<void> {
    return this.http.post<ApiResponse>(`${this.base}/register`, payload).pipe(
      map(res => {
        if (!res.success) throw new Error(res.message || 'Registration failed');
      }),
      catchError(this.handle)
    );
  }

  login(payload: { email: string; password: string }): Observable<string> {
    return this.http.post<ApiResponse<{ token: string }>>(`${this.base}/login`, payload).pipe(
      map(res => {
        if (!res.success || !res.data?.token) {
          throw new Error(res.message || 'Login failed');
        }
        return res.data.token;
      }),
      catchError(this.handle)
    );
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  clearToken(): void {
    localStorage.removeItem('token');
  }

  private handle(err: HttpErrorResponse) {
    // Try to surface backend ApiResponse message / validation map if present
    const msg =
      (err.error?.message as string) ||
      (typeof err.error === 'string' ? err.error : null) ||
      err.message ||
      'Something went wrong';
    return throwError(() => new Error(msg));
  }
}
