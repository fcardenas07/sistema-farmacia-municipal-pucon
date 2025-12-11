import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerDetalleInventarioMermasPagesComponent } from './ver-detalle-inventario-mermas-pages.component';

describe('VerDetalleInventarioMermasPagesComponent', () => {
  let component: VerDetalleInventarioMermasPagesComponent;
  let fixture: ComponentFixture<VerDetalleInventarioMermasPagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerDetalleInventarioMermasPagesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerDetalleInventarioMermasPagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
