import { TestBed } from '@angular/core/testing';

import { ProductosStockTotalService } from './productos-stock-total.service';

describe('ProductosStockTotalService', () => {
  let service: ProductosStockTotalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductosStockTotalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
