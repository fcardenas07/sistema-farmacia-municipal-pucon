import { TestBed } from '@angular/core/testing';

import { ProductosStockCriticoService } from './productos-stock-critico.service';

describe('ProductosStockCriticoService', () => {
  let service: ProductosStockCriticoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductosStockCriticoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
