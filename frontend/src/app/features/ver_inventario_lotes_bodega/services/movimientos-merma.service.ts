import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovimientoMermaPage } from '../models/movimiento-merma-page';

@Injectable({ providedIn: 'root' })
export class MovimientosMermaService {

  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/trazabilidad/movimientos';

  getMermas(page: number = 0): Observable<MovimientoMermaPage> {
    return this.http.get<MovimientoMermaPage>(
      `${this.apiUrl}?tipoMovimiento=MERMA&page=${page}`
    );
  }
}
