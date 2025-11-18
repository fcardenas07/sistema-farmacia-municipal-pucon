import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockCriticoPageComponent } from './stock-critico-page.component';

describe('StockCriticoPageComponent', () => {
  let component: StockCriticoPageComponent;
  let fixture: ComponentFixture<StockCriticoPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StockCriticoPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StockCriticoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
