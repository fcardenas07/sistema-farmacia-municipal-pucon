import { TestBed } from '@angular/core/testing';

import { ProductoFormService } from './producto-form.service';

describe('ProductoFormService', () => {
  let service: ProductoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductoFormService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
