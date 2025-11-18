import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResumenPedidoPageComponent } from './resumen-pedido-page.component';

describe('ResumenPedidoPageComponent', () => {
  let component: ResumenPedidoPageComponent;
  let fixture: ComponentFixture<ResumenPedidoPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResumenPedidoPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResumenPedidoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
