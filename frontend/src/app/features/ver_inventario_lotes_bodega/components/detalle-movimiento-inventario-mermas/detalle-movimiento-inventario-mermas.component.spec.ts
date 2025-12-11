import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleMovimientoInventarioMermasComponent } from './detalle-movimiento-inventario-mermas.component';

describe('DetalleMovimientoInventarioMermasComponent', () => {
  let component: DetalleMovimientoInventarioMermasComponent;
  let fixture: ComponentFixture<DetalleMovimientoInventarioMermasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleMovimientoInventarioMermasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleMovimientoInventarioMermasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
