import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EscaneoProductosComponent } from './escaneo-productos.component';

describe('EscaneoProductosComponent', () => {
  let component: EscaneoProductosComponent;
  let fixture: ComponentFixture<EscaneoProductosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EscaneoProductosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EscaneoProductosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
