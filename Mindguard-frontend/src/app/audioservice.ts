import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class Audioservice {
  private baseUrl = 'http://localhost:8080/api/audio';

  constructor(private http: HttpClient) {}

  getQuestions(): Observable<any> {
    return this.http.get(`${this.baseUrl}/questions`);
  }

  getAnswers(): Observable<any> {
    return this.http.get(`${this.baseUrl}/answers`);
  }

  submitAnswer(formData: FormData): Observable<any> {
    // Use responseType 'text' to accept plain string from backend
    return this.http.post(`${this.baseUrl}/submit-answer`, formData, { responseType: 'text' });
  }
  
}
