import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TotalProductosStockComponent } from './total-productos-stock.component';

describe('TotalProductosStockComponent', () => {
  let component: TotalProductosStockComponent;
  let fixture: ComponentFixture<TotalProductosStockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TotalProductosStockComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TotalProductosStockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
