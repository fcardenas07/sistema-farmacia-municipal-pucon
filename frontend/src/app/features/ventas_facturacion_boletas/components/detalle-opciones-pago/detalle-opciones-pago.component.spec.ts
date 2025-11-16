import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleOpcionesPagoComponent } from './detalle-opciones-pago.component';

describe('DetalleOpcionesPagoComponent', () => {
  let component: DetalleOpcionesPagoComponent;
  let fixture: ComponentFixture<DetalleOpcionesPagoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleOpcionesPagoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleOpcionesPagoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
