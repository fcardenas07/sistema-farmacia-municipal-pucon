import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QfHomeComponent } from './qf-home.component';

describe('QfHomeComponent', () => {
  let component: QfHomeComponent;
  let fixture: ComponentFixture<QfHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QfHomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QfHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
