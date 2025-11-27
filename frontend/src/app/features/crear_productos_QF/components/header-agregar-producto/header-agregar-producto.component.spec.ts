import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderAgregarProductoComponent } from './header-agregar-producto.component';

describe('HeaderAgregarProductoComponent', () => {
  let component: HeaderAgregarProductoComponent;
  let fixture: ComponentFixture<HeaderAgregarProductoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderAgregarProductoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderAgregarProductoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
