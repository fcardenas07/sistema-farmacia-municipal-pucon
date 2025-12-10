import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductosQfPageComponent } from './productos-qf-page.component';

describe('ProductosQfPageComponent', () => {
  let component: ProductosQfPageComponent;
  let fixture: ComponentFixture<ProductosQfPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProductosQfPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductosQfPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
