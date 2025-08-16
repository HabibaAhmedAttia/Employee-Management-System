import { Component } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  loading = false;
  serverMessage = '';

  form = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    email: ['', [Validators.required, Validators.email]],
    // Keep client-side a bit permissive; backend enforces the strict regex anyway
    password: ['', [
      Validators.required,
      Validators.minLength(6),
      Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&]).+$/)
    ]],
  });

  constructor(private fb: FormBuilder, private auth: AuthService, private router: Router) {}

  get f() { return this.form.controls; }

  submit() {
    this.serverMessage = '';
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.loading = true;
    this.auth.register(this.form.value as any).subscribe({
      next: () => {
        this.loading = false;
        alert('User registered successfully. Please login.');
        this.router.navigate(['/login']);
      },
      error: (err: Error) => {
        this.loading = false;
        this.serverMessage = err.message || 'Registration failed';
      }
    });
  }
}
