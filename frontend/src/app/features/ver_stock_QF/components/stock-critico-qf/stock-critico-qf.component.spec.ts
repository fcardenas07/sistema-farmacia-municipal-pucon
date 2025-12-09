import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockCriticoQfComponent } from './stock-critico-qf.component';

describe('StockCriticoQfComponent', () => {
  let component: StockCriticoQfComponent;
  let fixture: ComponentFixture<StockCriticoQfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StockCriticoQfComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StockCriticoQfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
