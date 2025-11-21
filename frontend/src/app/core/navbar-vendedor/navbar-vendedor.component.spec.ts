import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarVendedorComponent } from './navbar-vendedor.component';

describe('NavbarVendedorComponent', () => {
  let component: NavbarVendedorComponent;
  let fixture: ComponentFixture<NavbarVendedorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarVendedorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavbarVendedorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
