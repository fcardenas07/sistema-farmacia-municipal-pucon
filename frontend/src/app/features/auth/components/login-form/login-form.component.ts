import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
  personalCode = '';
  password = '';

  onSubmit() {
    console.log("Código ingresado:", this.personalCode);
    console.log("Contraseña:", this.password);

  
  }
}
