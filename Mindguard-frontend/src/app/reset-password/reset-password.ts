import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-reset-password',
  imports: [FormsModule],
  templateUrl: './reset-password.html',
  styleUrl: './reset-password.css'
})
export class ResetPassword {
username = '';

  newPassword = '';
  confirmNew = '';

  constructor(private http: HttpClient) {}

  reset() {
    if (!this.username || !this.newPassword) {
      alert('Fill all fields');
      return;
    }
    if (this.newPassword !== this.confirmNew) {
      alert('New passwords do not match');
      return;
    }

    this.http.post('http://localhost:8080/api/auth/reset-password', {
      username: this.username,
      
      newPassword: this.newPassword
    }, { responseType: 'text' })
    .subscribe(res => {
      if (res === 'success') {
        alert('Password changed successfully!');
      } else if (res === 'nouser') {
        alert('Username not found!');
      } else if (res === 'wrongold') {
        alert('Old password incorrect!');
      } else {
        alert('Reset failed!');
      }
    });
  }
}
