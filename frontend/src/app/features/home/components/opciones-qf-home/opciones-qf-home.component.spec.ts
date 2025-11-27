import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpcionesQfHomeComponent } from './opciones-qf-home.component';

describe('OpcionesQfHomeComponent', () => {
  let component: OpcionesQfHomeComponent;
  let fixture: ComponentFixture<OpcionesQfHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OpcionesQfHomeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OpcionesQfHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
