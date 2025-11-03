import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {
username = '';
  password = '';
  confirmpassword = '';
  role = '';  // NEW field

router= inject(Router);

  constructor(private http: HttpClient) {}
  goToLogin() {
    this.router.navigate(['/login']);
  }
  
  

  register() {
   if (!this.username || !this.password || !this.role) {
      alert('Please fill all fields');
      return;
    }
    if (this.password !== this.confirmpassword) {
      alert('Passwords do not match!');
      return;
    }
    

    this.http.post('http://localhost:8080/api/auth/register', 
       { username: this.username, password: this.password, role: this.role },
      { responseType: 'text' })
      .subscribe(response => {
        if (response === 'already') {
          alert('User already exists!');
        } else if (response === 'success') {
          alert('Successfully registered!');
          this.username = this.password = this.confirmpassword = this.role = '';
          this.router.navigate(['/login']);
        }
      }, () => alert('Registration failed!'));
  }
  
}



