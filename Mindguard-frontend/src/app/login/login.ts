import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
username = '';
  password = '';
  confirmpassword = '';

  constructor(private http: HttpClient, private router: Router) {}

  goToRegister() {
    this.router.navigate(['/register']);
  }

  goToReset() {
    this.router.navigate(['/reset-password']);
  }

  login() {
   if (!this.username || !this.password) {
      alert('Enter username and password');
      return;
    }

    const loginData = { username: this.username, password: this.password };

    this.http.post<{ status: string, role?: string }>('http://localhost:8080/api/auth/login', loginData, { withCredentials: true })
      .subscribe({
        next: res => {
          if (res.status === 'success') {
            localStorage.setItem('loggedInUser', this.username);
            sessionStorage.setItem('username', this.username); 
            if (res.role) localStorage.setItem('role', res.role);

            alert('Login successful!');
            this.router.navigate(['/assessment/quiz']);
          } else {
            alert('Invalid credentials');
          }
        },
        error: () => alert('Error logging in')
      });
  }
}
