import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IngresoCodigoPedidoPageComponent } from './ingreso-codigo-pedido-page.component';

describe('IngresoCodigoPedidoPageComponent', () => {
  let component: IngresoCodigoPedidoPageComponent;
  let fixture: ComponentFixture<IngresoCodigoPedidoPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IngresoCodigoPedidoPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IngresoCodigoPedidoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
