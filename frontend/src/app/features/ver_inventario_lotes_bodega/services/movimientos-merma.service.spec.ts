import { TestBed } from '@angular/core/testing';

import { MovimientosMermaService } from './movimientos-merma.service';

describe('MovimientosMermaService', () => {
  let service: MovimientosMermaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MovimientosMermaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
