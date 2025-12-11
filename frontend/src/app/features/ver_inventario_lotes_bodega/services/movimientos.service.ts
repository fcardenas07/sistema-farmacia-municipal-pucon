import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovimientoDetalle } from '../models/movimiento-detalle';

@Injectable({ providedIn: 'root' })
export class MovimientosService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/trazabilidad/movimientos';

  getMovimiento(id: string): Observable<MovimientoDetalle> {
    return this.http.get<MovimientoDetalle>(`${this.apiUrl}/${id}`);
  }
}
