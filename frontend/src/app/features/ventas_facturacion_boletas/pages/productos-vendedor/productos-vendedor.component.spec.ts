import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductosVendedorComponent } from './productos-vendedor.component';

describe('ProductosVendedorComponent', () => {
  let component: ProductosVendedorComponent;
  let fixture: ComponentFixture<ProductosVendedorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductosVendedorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductosVendedorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
