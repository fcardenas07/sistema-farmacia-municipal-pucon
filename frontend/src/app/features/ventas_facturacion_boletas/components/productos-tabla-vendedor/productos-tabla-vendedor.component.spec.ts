import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductosTablaVendedorComponent } from './productos-tabla-vendedor.component';

describe('ProductosTablaVendedorComponent', () => {
  let component: ProductosTablaVendedorComponent;
  let fixture: ComponentFixture<ProductosTablaVendedorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductosTablaVendedorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductosTablaVendedorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
