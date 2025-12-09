import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockCriticoQfPageComponent } from './stock-critico-qf-page.component';

describe('StockCriticoQfPageComponent', () => {
  let component: StockCriticoQfPageComponent;
  let fixture: ComponentFixture<StockCriticoQfPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StockCriticoQfPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StockCriticoQfPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
