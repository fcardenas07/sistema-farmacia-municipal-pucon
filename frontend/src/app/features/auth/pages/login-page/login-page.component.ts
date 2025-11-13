import { Component } from '@angular/core';
import { LoginBackgroundComponent } from "../../components/login-background/login-background.component";
import { LoginFormComponent } from "../../components/login-form/login-form.component";

@Component({
  selector: 'app-login-page',
  imports: [LoginBackgroundComponent, LoginFormComponent],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {

}
