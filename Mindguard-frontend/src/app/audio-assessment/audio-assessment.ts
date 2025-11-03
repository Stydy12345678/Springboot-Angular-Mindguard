import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { Audioservice } from '../audioservice';

@Component({
  selector: 'app-audio-assessment',
  imports: [CommonModule],
  templateUrl: './audio-assessment.html',
  styleUrl: './audio-assessment.css'
})
export class AudioAssessment implements OnInit {
    questions: any[] = [];
  answers: any[] = [];

  username = '';
  role = 'user';

  mediaRecorder: any;
  audioChunks: any = {};
  audioBlob: any = {};
  audioURL: any = {};
  isRecording: any = {};
  detectedMood: any = {}; // local detection (for UI display only)
  submittedQuestions: number[] = [];

  constructor(private audioService: Audioservice) {}

  ngOnInit() {
    this.username = sessionStorage.getItem('username') || '';  // ✅ ADDED ensure always from sessionStorage
    this.role = sessionStorage.getItem('role') || 'user';
    console.log('Logged in as', this.username, 'Role:', this.role);

    if (!this.username) {                                      // ✅ ADDED prevent blank username
      alert('Please log in first before using audio assessment.');
    }

    this.loadQuestions();
    this.loadAnswers();
  }

  // 🔹 Load all questions
  loadQuestions() {
    this.audioService.getQuestions().subscribe({
      next: data => this.questions = data,
      error: err => console.error('Error loading questions', err)
    });
  }

  // 🔹 Load all answers
  loadAnswers() {
    this.audioService.getAnswers().subscribe({
      next: data => this.answers = data,
      error: err => console.error('Error loading answers', err)
    });
  }

  // 🎙 Start recording
  startRecording(qId: number) {
    if (this.role.toLowerCase() !== 'user') {
      alert('Only users can record answers.');
      return;
    }

    navigator.mediaDevices.getUserMedia({ audio: true }).then(stream => {
      this.mediaRecorder = new MediaRecorder(stream);
      this.audioChunks[qId] = [];
      this.isRecording[qId] = true;

      this.mediaRecorder.ondataavailable = (e: any) => this.audioChunks[qId].push(e.data);
      this.mediaRecorder.onstop = async () => {
        this.audioBlob[qId] = new Blob(this.audioChunks[qId], { type: 'audio/wav' });
        this.audioURL[qId] = URL.createObjectURL(this.audioBlob[qId]);
        this.isRecording[qId] = false;

        // Optional: local detection for UI preview only
        this.detectMoodFromAudio(this.audioBlob[qId], qId);
      };

      this.mediaRecorder.start();
    }).catch(err => {
      console.error('Microphone access denied:', err);
      alert('Microphone access is required to record audio.');
    });
  }

  // ⏹ Stop recording
  stopRecording(qId: number) {
    if (this.mediaRecorder) {
      this.mediaRecorder.stop();
    }
  }

  // 🎧 Optional: simple local mood detection (for display only)
  async detectMoodFromAudio(blob: Blob, qId: number) {
    try {
      const arrayBuffer = await blob.arrayBuffer();
      const audioCtx = new AudioContext();
      const audioData = await audioCtx.decodeAudioData(arrayBuffer);

      const channelData = audioData.getChannelData(0);
      const rms = Math.sqrt(channelData.reduce((a, b) => a + b * b, 0) / channelData.length);
      const duration = audioData.duration;

      let mood = 'Neutral';
      if (rms > 0.05 && duration < 3) mood = 'Angry';
      else if (rms < 0.02 && duration > 4) mood = 'Sad';
      else if (rms > 0.04 && duration > 3) mood = 'Happy';
      else if (rms < 0.015) mood = 'Depressed';
      else if (rms > 0.03 && duration > 5) mood = 'Motivated';
      else if (rms < 0.03 && duration < 3) mood = 'Stressed';

      this.detectedMood[qId] = mood;
      console.log(`Detected mood for Q${qId}: ${mood}`);
    } catch (err) {
      console.error('Local mood detection failed:', err);
      this.detectedMood[qId] = 'Unknown';
    }
  }

  // 📤 Submit the recorded answer to backend
  submitRecording(qId: number) {
    if (this.role.toLowerCase() !== 'user') {
      alert('Only users can submit answers.');
      return;
    }

    if (!this.audioBlob[qId]) {
      alert('Please record audio first.');
      return;
    }

    // ✅ ADDED: Ensure username and mood always set correctly
    const file = new File([this.audioBlob[qId]], `answer_q${qId}.wav`, { type: 'audio/wav' });
    const formData = new FormData();
    formData.append('file', file);
    formData.append('questionId', qId.toString());
    formData.append('username', this.username);   // ✅ ADDED ensure correct logged-in user stored
    formData.append('role', this.role);
    formData.append('mood', this.detectedMood[qId] || 'Unknown'); // ✅ ADDED send detected mood

    console.log('Submitting data:', {
      username: this.username,
      role: this.role,
      mood: this.detectedMood[qId],
      questionId: qId
    }); // ✅ ADDED debugging log

    this.audioService.submitAnswer(formData).subscribe({
      next: res => {
        alert(res);
        this.submittedQuestions.push(qId);
        this.loadAnswers();

        // Pulse animation for short time
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

  // ✅ Helper methods for UI
  isSubmitted(qId: number): boolean {
    return this.answers.some(a => a.questionId === qId && a.username === this.username);
  }

  isPulsing(qId: number): boolean {
    return this.submittedQuestions.includes(qId);
  }
}