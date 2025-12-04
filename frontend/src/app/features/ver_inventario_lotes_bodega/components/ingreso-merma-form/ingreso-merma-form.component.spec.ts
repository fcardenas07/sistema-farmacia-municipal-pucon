import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IngresoMermaFormComponent } from './ingreso-merma-form.component';

describe('IngresoMermaFormComponent', () => {
  let component: IngresoMermaFormComponent;
  let fixture: ComponentFixture<IngresoMermaFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [IngresoMermaFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IngresoMermaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
