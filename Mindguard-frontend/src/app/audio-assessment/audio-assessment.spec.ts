import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AudioAssessment } from './audio-assessment';

describe('AudioAssessment', () => {
  let component: AudioAssessment;
  let fixture: ComponentFixture<AudioAssessment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AudioAssessment]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AudioAssessment);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
