import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormCodigoPedidoComponent } from './form-codigo-pedido.component';

describe('FormCodigoPedidoComponent', () => {
  let component: FormCodigoPedidoComponent;
  let fixture: ComponentFixture<FormCodigoPedidoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormCodigoPedidoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormCodigoPedidoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
