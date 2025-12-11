import { TestBed } from '@angular/core/testing';

import { MermasService } from './mermas.service';

describe('MermasService', () => {
  let service: MermasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MermasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
