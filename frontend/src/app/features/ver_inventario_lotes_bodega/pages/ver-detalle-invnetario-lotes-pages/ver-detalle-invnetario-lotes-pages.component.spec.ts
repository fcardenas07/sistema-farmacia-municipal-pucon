import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerDetalleInvnetarioLotesPagesComponent } from './ver-detalle-invnetario-lotes-pages.component';

describe('VerDetalleInvnetarioLotesPagesComponent', () => {
  let component: VerDetalleInvnetarioLotesPagesComponent;
  let fixture: ComponentFixture<VerDetalleInvnetarioLotesPagesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerDetalleInvnetarioLotesPagesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerDetalleInvnetarioLotesPagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
