import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderStocknormalQfComponent } from './header-stocknormal-qf.component';

describe('HeaderStocknormalQfComponent', () => {
  let component: HeaderStocknormalQfComponent;
  let fixture: ComponentFixture<HeaderStocknormalQfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderStocknormalQfComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderStocknormalQfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
