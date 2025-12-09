import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardBodegaComponent } from './dashboard-bodega.component';

describe('DashboardBodegaComponent', () => {
  let component: DashboardBodegaComponent;
  let fixture: ComponentFixture<DashboardBodegaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DashboardBodegaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DashboardBodegaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
