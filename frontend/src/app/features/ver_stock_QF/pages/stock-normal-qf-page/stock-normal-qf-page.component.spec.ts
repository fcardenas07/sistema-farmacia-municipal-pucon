import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockNormalQfPageComponent } from './stock-normal-qf-page.component';

describe('StockNormalQfPageComponent', () => {
  let component: StockNormalQfPageComponent;
  let fixture: ComponentFixture<StockNormalQfPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StockNormalQfPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StockNormalQfPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
