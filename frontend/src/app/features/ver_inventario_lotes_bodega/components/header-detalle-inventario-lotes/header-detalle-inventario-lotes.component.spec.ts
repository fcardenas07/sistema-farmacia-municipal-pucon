import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderDetalleInventarioLotesComponent } from './header-detalle-inventario-lotes.component';

describe('HeaderDetalleInventarioLotesComponent', () => {
  let component: HeaderDetalleInventarioLotesComponent;
  let fixture: ComponentFixture<HeaderDetalleInventarioLotesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderDetalleInventarioLotesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderDetalleInventarioLotesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
