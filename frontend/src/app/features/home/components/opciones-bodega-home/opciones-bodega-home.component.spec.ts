import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpcionesBodegaHomeComponent } from './opciones-bodega-home.component';

describe('OpcionesBodegaHomeComponent', () => {
  let component: OpcionesBodegaHomeComponent;
  let fixture: ComponentFixture<OpcionesBodegaHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OpcionesBodegaHomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OpcionesBodegaHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
