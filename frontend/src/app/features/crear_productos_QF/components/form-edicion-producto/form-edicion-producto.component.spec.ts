import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormEdicionProductoComponent } from './form-edicion-producto.component';

describe('FormEdicionProductoComponent', () => {
  let component: FormEdicionProductoComponent;
  let fixture: ComponentFixture<FormEdicionProductoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormEdicionProductoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormEdicionProductoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
