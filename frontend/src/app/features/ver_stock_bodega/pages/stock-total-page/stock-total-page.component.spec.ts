import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockTotalPageComponent } from './stock-total-page.component';

describe('StockTotalPageComponent', () => {
  let component: StockTotalPageComponent;
  let fixture: ComponentFixture<StockTotalPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StockTotalPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StockTotalPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
