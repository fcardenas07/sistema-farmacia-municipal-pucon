import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderStockcriticoQfComponent } from './header-stockcritico-qf.component';

describe('HeaderStockcriticoQfComponent', () => {
  let component: HeaderStockcriticoQfComponent;
  let fixture: ComponentFixture<HeaderStockcriticoQfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderStockcriticoQfComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderStockcriticoQfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
