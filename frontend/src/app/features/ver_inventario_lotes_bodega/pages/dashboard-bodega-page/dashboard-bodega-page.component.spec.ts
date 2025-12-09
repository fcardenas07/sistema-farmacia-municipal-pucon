import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardBodegaPageComponent } from './dashboard-bodega-page.component';

describe('DashboardBodegaPageComponent', () => {
  let component: DashboardBodegaPageComponent;
  let fixture: ComponentFixture<DashboardBodegaPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardBodegaPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardBodegaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
