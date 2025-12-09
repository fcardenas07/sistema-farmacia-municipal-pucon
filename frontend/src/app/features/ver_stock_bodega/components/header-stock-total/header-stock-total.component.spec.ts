import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderStockTotalComponent } from './header-stock-total.component';

describe('HeaderStockTotalComponent', () => {
  let component: HeaderStockTotalComponent;
  let fixture: ComponentFixture<HeaderStockTotalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderStockTotalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderStockTotalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
