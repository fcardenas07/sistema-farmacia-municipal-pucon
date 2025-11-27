import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductoResumenComponent } from './producto-resumen.component';

describe('ProductoResumenComponent', () => {
  let component: ProductoResumenComponent;
  let fixture: ComponentFixture<ProductoResumenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductoResumenComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductoResumenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
