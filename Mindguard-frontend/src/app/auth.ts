import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  currentUser: any;
  isLoggedIn() {
    throw new Error('Method not implemented.');
  }
  constructor(private http: HttpClient) { }

  login(username: string, password: string) {
    return this.http.post('http://localhost:8080/login', { username, password }, { responseType: 'text' });
  }

  setRole(role: string) {
    localStorage.setItem('role', role);
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

 
  getCurrentUser() {
  // Check both places for username
  const username = localStorage.getItem('username') || localStorage.getItem('loggedInUser') || sessionStorage.getItem('username');
  const role = localStorage.getItem('role'); // role is stored in localStorage
  if (username && role) return { username, role };
  return null;
}

  isUser(): boolean {
    return this.getRole() === 'USER';
  }

  isAdmin(): boolean {
  return this.getCurrentUser()?.role?.toUpperCase() === 'ADMIN';
}


  logout() {
    localStorage.removeItem('username');
    localStorage.removeItem('role');
  }
  
}

  
