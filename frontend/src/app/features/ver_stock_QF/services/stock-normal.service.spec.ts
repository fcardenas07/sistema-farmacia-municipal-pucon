import { TestBed } from '@angular/core/testing';

import { StockNormalService } from './stock-normal.service';

describe('StockNormalService', () => {
  let service: StockNormalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StockNormalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
