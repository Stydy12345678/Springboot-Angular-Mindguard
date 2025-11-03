import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
interface Question {
  questionId: number;
  text: string;
  options: string[];
}

interface Answer {
  questionId: number;
  selectedOptionIndex: number | null;
}
@Component({
  selector: 'app-quiz-assessment',
  imports: [FormsModule,CommonModule],
  templateUrl: './quiz-assessment.html',
  styleUrl: './quiz-assessment.css'
})
export class QuizAssessment implements OnInit {
 router = inject(Router);
  http = inject(HttpClient);

  questions: Question[] = [
    { questionId: 1, text: 'How often do you feel stressed or overwhelmed?', options: ['Never', 'Sometimes', 'Often', 'Always'] },
    { questionId: 2, text: 'How would you rate your recent sleep quality?', options: ['Very good', 'Good', 'Poor', 'Very poor'] },
    { questionId: 3, text: 'Do you find it hard to concentrate on tasks?', options: ['Never', 'Rarely', 'Sometimes', 'Often'] },
    { questionId: 4, text: 'How often do you feel lonely?', options: ['Never', 'Rarely', 'Sometimes', 'Often'] },
    { questionId: 5, text: 'How is your current level of physical energy?', options: ['Very high', 'Moderate', 'Low', 'Very low'] },
    { questionId: 6, text: 'How often do you feel anxious or worried?', options: ['Never', 'Sometimes', 'Often', 'Always'] },
    { questionId: 7, text: 'Do you feel supported by friends or family?', options: ['Always', 'Often', 'Sometimes', 'Never'] },
    { questionId: 8, text: 'How do you usually feel about your day ahead?', options: ['Excited', 'Neutral', 'Tired', 'Hopeless'] },
    { questionId: 9, text: 'How often do you engage in relaxing activities?', options: ['Daily', 'Weekly', 'Rarely', 'Never'] },
    { questionId: 10, text: 'Overall, how would you describe your current mental health?', options: ['Excellent', 'Good', 'Fair', 'Poor'] }
  ];

  answers: Answer[] = [];
  successMessage = '';
  moodResult = '';
  message = '';
  role: string = '';
  canSubmit: boolean = false;
  moodHistory: any[] = [];

  ngOnInit() {
    const username = localStorage.getItem('loggedInUser');
    const role = localStorage.getItem('role');

    if (!username || !role) {
      alert('Please login first!');
      this.router.navigate(['/login']);
      return;
    }

    this.role = role;
    this.canSubmit = this.role === 'user';
    this.answers = this.questions.map(q => ({ questionId: q.questionId, selectedOptionIndex: null }));
  }

  get allAnswered(): boolean {
    return this.answers.every(a => a.selectedOptionIndex !== null);
  }

  submitQuiz() {
    if (!this.canSubmit) {
      alert('Only users can submit the quiz.');
      return;
    }

    const username = localStorage.getItem('loggedInUser');
    if (!username) {
      alert('User not logged in!');
      return;
    }

    if (!this.allAnswered) {
      alert('Please answer all questions before submitting.');
      return;
    }

    const payload = this.answers.map(a => ({
      userIdentifier: username,
      quizId: 1,
      questionId: a.questionId,
      selectedOption: this.questions.find(q => q.questionId === a.questionId)?.options[a.selectedOptionIndex!]
    }));

    this.http.post('http://localhost:8080/api/quiz/submit', payload)
      .subscribe({
        next: (res: any) => {
          this.successMessage = '✅ Quiz submitted successfully!';
          this.moodResult = res.mood;
          this.message = res.message;
        },
        error: err => alert(err.error || 'Error submitting quiz.')
      });
  }

  loadMoodHistory() {
    const username = localStorage.getItem('loggedInUser');
    const role = localStorage.getItem('role');
    if (!username) return;

    let url = role === 'user'
      ? `http://localhost:8080/api/quiz/mood/${username}`
      : `http://localhost:8080/api/quiz/mood/all`;

    this.http.get(url).subscribe({
      next: (res: any) => this.moodHistory = res,
      error: err => alert(err.error || 'Error fetching mood history.')
    });
  }
}
