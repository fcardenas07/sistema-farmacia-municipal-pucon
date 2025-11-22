import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderResumenCreacionProductoComponent } from './header-resumen-creacion-producto.component';

describe('HeaderResumenCreacionProductoComponent', () => {
  let component: HeaderResumenCreacionProductoComponent;
  let fixture: ComponentFixture<HeaderResumenCreacionProductoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderResumenCreacionProductoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderResumenCreacionProductoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
