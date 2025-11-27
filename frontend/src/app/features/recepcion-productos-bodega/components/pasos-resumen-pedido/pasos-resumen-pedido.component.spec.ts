import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasosResumenPedidoComponent } from './pasos-resumen-pedido.component';

describe('PasosResumenPedidoComponent', () => {
  let component: PasosResumenPedidoComponent;
  let fixture: ComponentFixture<PasosResumenPedidoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PasosResumenPedidoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PasosResumenPedidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
