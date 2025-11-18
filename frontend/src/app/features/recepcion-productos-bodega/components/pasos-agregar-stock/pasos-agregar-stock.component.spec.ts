import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasosAgregarStockComponent } from './pasos-agregar-stock.component';

describe('PasosAgregarStockComponent', () => {
  let component: PasosAgregarStockComponent;
  let fixture: ComponentFixture<PasosAgregarStockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PasosAgregarStockComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PasosAgregarStockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
