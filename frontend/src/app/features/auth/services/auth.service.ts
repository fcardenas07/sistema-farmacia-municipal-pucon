import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, map } from 'rxjs';
import { Router } from '@angular/router';
import {
  AuthResponse,
  LoginRequest,
  Usuario,
  Rol,
} from '../../../shared/models/auth.models';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';
  private currentUserSubject = new BehaviorSubject<Usuario | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();
  private tokenKey = 'auth_token';
  private userKey = 'current_user';

  constructor(private http: HttpClient, private router: Router) {
    this.loadStoredAuthData();
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        map((response) => {
          this.storeAuthData(response);
          return response;
        })
      );
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getCurrentUser(): Usuario | null {
    return this.currentUserSubject.value;
  }

  getCurrentUserRol(): Rol | null {
    return this.currentUserSubject.value?.rol || null;
  }

  isAuthenticated(): boolean {
    return !!this.getToken() && !!this.getCurrentUser();
  }

  hasRole(requiredRoles: Rol[]): boolean {
    const userRol = this.getCurrentUserRol();
    return userRol ? requiredRoles.includes(userRol) : false;
  }

  // Métodos específicos por rol
  isAdmin(): boolean {
    return this.hasRole(['ADMIN']);
  }

  isBodeguero(): boolean {
    return this.hasRole(['BODEGUERO']);
  }

  isQf(): boolean {
    return this.hasRole(['QF']);
  }

  isVendedor(): boolean {
    return this.hasRole(['VENDEDOR']);
  }

  // Determinar el home según el rol
  getHomeRoute(): string {
    const rol = this.getCurrentUserRol();
    switch (rol) {
      case 'ADMIN':
        return '/home-admin';
      case 'BODEGUERO':
        return '/dashboard-bodega';
      case 'QF':
        return '/home-qf';
      case 'VENDEDOR':
        return '/productos-vendedor';
      default:
        return '/login';
    }
  }

  private storeAuthData(response: AuthResponse): void {
    localStorage.setItem(this.tokenKey, response.token);
    const usuario: Usuario = {
      idUsuario: response.idUsuario,
      username: response.username,
      nombreCompleto: response.nombreCompleto,
      email: response.email,
      rol: response.rol,
    };
    localStorage.setItem(this.userKey, JSON.stringify(usuario));
    this.currentUserSubject.next(usuario);
  }

  private loadStoredAuthData(): void {
    const token = localStorage.getItem(this.tokenKey);
    const userStr = localStorage.getItem(this.userKey);

    if (token && userStr) {
      try {
        const user = JSON.parse(userStr);
        this.currentUserSubject.next(user);
      } catch (e) {
        this.clearAuthData();
      }
    }
  }

  private clearAuthData(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
  }
}
