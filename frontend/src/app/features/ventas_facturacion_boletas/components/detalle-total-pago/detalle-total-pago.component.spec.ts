import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleTotalPagoComponent } from './detalle-total-pago.component';

describe('DetalleTotalPagoComponent', () => {
  let component: DetalleTotalPagoComponent;
  let fixture: ComponentFixture<DetalleTotalPagoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleTotalPagoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetalleTotalPagoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
