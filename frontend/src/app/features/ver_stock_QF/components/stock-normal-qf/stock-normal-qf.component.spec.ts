import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockNormalQfComponent } from './stock-normal-qf.component';

describe('StockNormalQfComponent', () => {
  let component: StockNormalQfComponent;
  let fixture: ComponentFixture<StockNormalQfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StockNormalQfComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StockNormalQfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
