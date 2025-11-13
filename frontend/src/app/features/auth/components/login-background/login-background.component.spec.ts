import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginBackgroundComponent } from './login-background.component';

describe('LoginBackgroundComponent', () => {
  let component: LoginBackgroundComponent;
  let fixture: ComponentFixture<LoginBackgroundComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginBackgroundComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginBackgroundComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
