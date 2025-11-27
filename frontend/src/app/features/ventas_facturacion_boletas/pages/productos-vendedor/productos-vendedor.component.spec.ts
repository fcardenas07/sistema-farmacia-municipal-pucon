import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductosVendedorPageComponent } from './productos-vendedor.component';

describe('ProductosVendedorComponent', () => {
  let component: ProductosVendedorPageComponent;
  let fixture: ComponentFixture<ProductosVendedorPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductosVendedorPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductosVendedorPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
