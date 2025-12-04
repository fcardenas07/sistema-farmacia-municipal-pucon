import { TestBed } from '@angular/core/testing';

import { StockCriticoService } from './stock-critico.service';

describe('StockCriticoService', () => {
  let service: StockCriticoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StockCriticoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
