import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResumenCreacionProductoPageComponent } from './resumen-creacion-producto-page.component';

describe('ResumenCreacionProductoPageComponent', () => {
  let component: ResumenCreacionProductoPageComponent;
  let fixture: ComponentFixture<ResumenCreacionProductoPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResumenCreacionProductoPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResumenCreacionProductoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
