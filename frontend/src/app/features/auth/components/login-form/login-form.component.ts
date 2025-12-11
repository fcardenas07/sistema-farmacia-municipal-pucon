// src/app/features/auth/components/login-form/login-form.component.ts
import {Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {LoginRequest} from '../../../../shared/models/auth.models';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [FormsModule, CommonModule, NgOptimizedImage],
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent {
  username = ''; // Cambié de personalCode a username
  password = '';
  isLoading = false;
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  onSubmit() {
    if (!this.username || !this.password) {
      this.errorMessage = 'Por favor ingresa usuario y contraseña';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    const loginRequest: LoginRequest = {
      username: this.username,
      password: this.password
    };

    this.authService.login(loginRequest).subscribe({
      next: (response) => {
        this.isLoading = false;
        const homeRoute = this.authService.getHomeRoute();
        this.router.navigate([homeRoute]);
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMessage = error.status === 401
          ? 'Credenciales inválidas. Por favor verifica tus datos.'
          : 'Error en el servidor. Intenta nuevamente.';
        console.error('Login error:', error);
      }
    });
  }
}
