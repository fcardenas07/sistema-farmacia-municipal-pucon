import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IngresoPage } from '../models/ingreso-page';

@Injectable({ providedIn: 'root' })
export class IngresosService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/trazabilidad/ingresos';

  getIngresos(page: number): Observable<IngresoPage> {
    return this.http.get<IngresoPage>(`${this.apiUrl}?page=${page}`);
  }
}
