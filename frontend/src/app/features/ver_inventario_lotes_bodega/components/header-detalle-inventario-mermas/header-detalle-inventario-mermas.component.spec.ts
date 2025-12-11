import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderDetalleInventarioMermasComponent } from './header-detalle-inventario-mermas.component';

describe('HeaderDetalleInventarioMermasComponent', () => {
  let component: HeaderDetalleInventarioMermasComponent;
  let fixture: ComponentFixture<HeaderDetalleInventarioMermasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderDetalleInventarioMermasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderDetalleInventarioMermasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
