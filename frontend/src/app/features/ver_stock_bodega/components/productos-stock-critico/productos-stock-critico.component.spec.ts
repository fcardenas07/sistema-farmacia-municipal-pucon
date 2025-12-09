import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductosStockCriticoComponent } from './productos-stock-critico.component';

describe('ProductosStockCriticoComponent', () => {
  let component: ProductosStockCriticoComponent;
  let fixture: ComponentFixture<ProductosStockCriticoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductosStockCriticoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductosStockCriticoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
