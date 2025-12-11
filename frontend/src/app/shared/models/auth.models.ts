export interface AuthResponse {
  token: string;
  idUsuario: string;
  username: string;
  nombreCompleto: string;
  email: string;
  rol: Rol;
}

export type Rol = 'ADMIN' | 'BODEGUERO' | 'QF' | 'VENDEDOR';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface Usuario {
  idUsuario: string;
  username: string;
  nombreCompleto: string;
  email: string;
  rol: Rol;
}
