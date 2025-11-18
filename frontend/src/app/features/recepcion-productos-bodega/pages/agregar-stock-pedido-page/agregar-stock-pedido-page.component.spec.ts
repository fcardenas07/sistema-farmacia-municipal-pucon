import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgregarStockPedidoPageComponent } from './agregar-stock-pedido-page.component';

describe('AgregarStockPedidoPageComponent', () => {
  let component: AgregarStockPedidoPageComponent;
  let fixture: ComponentFixture<AgregarStockPedidoPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgregarStockPedidoPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgregarStockPedidoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
