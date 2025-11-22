import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormCreacionProductoComponent } from './form-creacion-producto.component';

describe('FormCreacionProductoComponent', () => {
  let component: FormCreacionProductoComponent;
  let fixture: ComponentFixture<FormCreacionProductoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormCreacionProductoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormCreacionProductoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
