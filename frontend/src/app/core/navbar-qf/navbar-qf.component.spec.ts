import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarQfComponent } from './navbar-qf.component';

describe('NavbarQfComponent', () => {
  let component: NavbarQfComponent;
  let fixture: ComponentFixture<NavbarQfComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarQfComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavbarQfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
