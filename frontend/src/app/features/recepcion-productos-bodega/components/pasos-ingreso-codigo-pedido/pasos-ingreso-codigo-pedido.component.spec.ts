import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasosIngresoCodigoPedidoComponent } from './pasos-ingreso-codigo-pedido.component';

describe('PasosIngresoCodigoPedidoComponent', () => {
  let component: PasosIngresoCodigoPedidoComponent;
  let fixture: ComponentFixture<PasosIngresoCodigoPedidoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PasosIngresoCodigoPedidoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PasosIngresoCodigoPedidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
