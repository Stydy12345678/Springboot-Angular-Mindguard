import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizAssessment } from './quiz-assessment';

describe('QuizAssessment', () => {
  let component: QuizAssessment;
  let fixture: ComponentFixture<QuizAssessment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QuizAssessment]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuizAssessment);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
