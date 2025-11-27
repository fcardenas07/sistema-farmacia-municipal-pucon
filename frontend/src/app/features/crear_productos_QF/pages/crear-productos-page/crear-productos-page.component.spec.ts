import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearProductosPageComponent } from './crear-productos-page.component';

describe('CrearProductosPageComponent', () => {
  let component: CrearProductosPageComponent;
  let fixture: ComponentFixture<CrearProductosPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearProductosPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearProductosPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
