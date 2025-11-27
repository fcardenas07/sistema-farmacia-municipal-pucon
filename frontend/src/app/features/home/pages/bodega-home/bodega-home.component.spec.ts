import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BodegaHomeComponent } from './bodega-home.component';

describe('BodegaHomeComponent', () => {
  let component: BodegaHomeComponent;
  let fixture: ComponentFixture<BodegaHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BodegaHomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BodegaHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
