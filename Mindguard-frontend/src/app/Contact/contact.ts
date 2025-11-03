import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-contact',
  imports: [FormsModule,CommonModule],
  templateUrl: './contact.html',
  styleUrl: './contact.css'
})
export class Contact {
 faqs = [
    { question: 'How quickly will I receive a response?', answer: 'We usually respond within 24 hours during business days.', open: false },
    { question: 'Is my information kept confidential?', answer: 'Yes, all communications are confidential and secure.', open: false },
    { question: 'Do I need insurance to use MindGuard?', answer: 'No, MindGuard is accessible to everyone without insurance.', open: false },
    { question: "What if I'm having a mental health emergency?", answer: 'Call 988 (Suicide & Crisis Lifeline) or 911 immediately.', open: false },
    { question: 'Can I contact you outside of business hours?', answer: 'Yes, crisis support is available 24/7.', open: false }
  ];
}
