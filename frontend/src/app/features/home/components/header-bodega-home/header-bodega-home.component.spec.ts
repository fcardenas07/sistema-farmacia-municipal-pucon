import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderBodegaHomeComponent } from './header-bodega-home.component';

describe('HeaderBodegaHomeComponent', () => {
  let component: HeaderBodegaHomeComponent;
  let fixture: ComponentFixture<HeaderBodegaHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderBodegaHomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderBodegaHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
