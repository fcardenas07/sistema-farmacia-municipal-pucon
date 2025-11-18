import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderStockComponent } from './header-stock.component';

describe('HeaderStockComponent', () => {
  let component: HeaderStockComponent;
  let fixture: ComponentFixture<HeaderStockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderStockComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderStockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
