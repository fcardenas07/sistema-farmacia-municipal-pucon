import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormularioMermaPageComponent } from './formulario-merma-page.component';

describe('FormularioMermaPageComponent', () => {
  let component: FormularioMermaPageComponent;
  let fixture: ComponentFixture<FormularioMermaPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormularioMermaPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormularioMermaPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
