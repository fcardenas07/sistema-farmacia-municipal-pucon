import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderIngresoCodigoPedidoComponent } from './header-ingreso-codigo-pedido.component';

describe('HeaderIngresoCodigoPedidoComponent', () => {
  let component: HeaderIngresoCodigoPedidoComponent;
  let fixture: ComponentFixture<HeaderIngresoCodigoPedidoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderIngresoCodigoPedidoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderIngresoCodigoPedidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
