import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleVentaVendedorComponent } from './detalle-venta-vendedor.component';

describe('DetalleVentaVendedorComponent', () => {
  let component: DetalleVentaVendedorComponent;
  let fixture: ComponentFixture<DetalleVentaVendedorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleVentaVendedorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleVentaVendedorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
