import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleMovimientoInventarioLoteComponent } from './detalle-movimiento-inventario-lote.component';

describe('DetalleMovimientoInventarioLoteComponent', () => {
  let component: DetalleMovimientoInventarioLoteComponent;
  let fixture: ComponentFixture<DetalleMovimientoInventarioLoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleMovimientoInventarioLoteComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleMovimientoInventarioLoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
