import { Component, OnInit } from '@angular/core';
import { Audioservice } from '../audioservice';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-audio-assessment',
  imports: [CommonModule,FormsModule],
  templateUrl: './audio-assessment.html',
  styleUrl: './audio-assessment.css'
})
export class AudioAssessment implements OnInit {
  // Store all questions and answers
  questions: any[] = [];
  answers: any[] = [];

  // Current logged-in user's role and username
  role = 'user';
  username = '';

  // MediaRecorder & audio data
  mediaRecorder: any;
  audioChunks: any = {}; // store chunks per question
  audioBlob: any = {};   // final audio blob per question
  audioURL: any = {};    // preview URL per question
  isRecording: any = {}; // track recording state per question

  // Track newly submitted questions to trigger pulse animation
  submittedQuestions: number[] = [];

  constructor(private audioService: Audioservice) {}

  ngOnInit() {
    // Get username and role from sessionStorage (stored at login)
    this.username = sessionStorage.getItem('username') || '';
    this.role = sessionStorage.getItem('role') || 'user';

    console.log('Logged-in username:', this.username, 'Role:', this.role);

    // Load questions and previous answers
    this.loadQuestions();
    this.loadAnswers();
  }

  // Fetch all questions from backend
  loadQuestions() {
    this.audioService.getQuestions().subscribe({
      next: data => this.questions = data,
      error: err => console.error('Error loading questions', err)
    });
  }

  // Fetch all answers from backend
  loadAnswers() {
    this.audioService.getAnswers().subscribe({
      next: data => this.answers = data,
      error: err => console.error('Error loading answers', err)
    });
  }

  // Start recording for a question
  startRecording(qId: number) {
    if (this.role !== 'user') return; // only users can record

    navigator.mediaDevices.getUserMedia({ audio: true }).then(stream => {
      this.mediaRecorder = new MediaRecorder(stream);
      this.audioChunks[qId] = [];
      this.isRecording[qId] = true; // mark as recording

      // Collect audio chunks
      this.mediaRecorder.ondataavailable = (e: any) => this.audioChunks[qId].push(e.data);

      // When recording stops, create Blob and preview URL
      this.mediaRecorder.onstop = () => {
        this.audioBlob[qId] = new Blob(this.audioChunks[qId], { type: 'audio/wav' });
        this.audioURL[qId] = URL.createObjectURL(this.audioBlob[qId]);
        this.isRecording[qId] = false;
      };

      this.mediaRecorder.start();
    });
  }

  // Stop recording
  stopRecording(qId: number) {
    if (this.mediaRecorder) this.mediaRecorder.stop();
  }

  // Submit recorded audio
  submitRecording(qId: number) {
    if (this.role !== 'user') {
      alert('Only users can submit answers!');
      return;
    }

    if (!this.audioBlob[qId]) {
      alert('Please record audio first!');
      return;
    }

    // Prepare file for submission
    const file = new File([this.audioBlob[qId]], `answer_q${qId}.wav`, { type: 'audio/wav' });
    const formData = new FormData();
    formData.append('file', file);
    formData.append('questionId', qId.toString());
    formData.append('username', this.username);
    formData.append('role', this.role);

    // Submit to backend
    this.audioService.submitAnswer(formData).subscribe({
      next: res => {
        alert(res);                     // show backend message
        this.submittedQuestions.push(qId); // trigger pulse animation
        this.loadAnswers();              // reload all answers

        // Remove pulse class after 1 second
        setTimeout(() => {
          this.submittedQuestions = this.submittedQuestions.filter(id => id !== qId);
        }, 1000);
      },
      error: err => {
        console.error('Upload error:', err);
        alert('Upload failed! See console for details.');
      }
    });
  }

  // Check if the question is already submitted by this user
  isSubmitted(qId: number): boolean {
    return this.answers.some(a => a.questionId === qId && a.username === this.username);
  }

  // Check if pulse animation should play
  isPulsing(qId: number): boolean {
    return this.submittedQuestions.includes(qId);
  }
  
}

