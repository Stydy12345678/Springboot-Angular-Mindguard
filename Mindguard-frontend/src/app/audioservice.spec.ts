import { TestBed } from '@angular/core/testing';

import { Audioservice } from './audioservice';

describe('Audioservice', () => {
  let service: Audioservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Audioservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
