import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { RespuestaPaginada } from '../models/respuesta-paginada';
import { ProductoFiltrado } from '../models/producto-filtrado';

@Injectable({
  providedIn: 'root'
})
export class ProductosStockTotalService {

  private API_URL = 'http://localhost:8080/productos/buscar';

  constructor(private http: HttpClient) {}

  buscarStockTotal(
    pagina: number,
    nombreComercial?: string,
    nombreGenerico?: string
  ): Observable<RespuestaPaginada<ProductoFiltrado>> {

    let params = new HttpParams()
      .set('pagina', pagina)
      .set('tipoStock', 'NORMAL'); // ← filtro obligatorio

    if (nombreComercial && nombreComercial.trim() !== '') {
      params = params.set('nombreComercial', nombreComercial);
    }

    if (nombreGenerico && nombreGenerico.trim() !== '') {
      params = params.set('nombreGenerico', nombreGenerico);
    }

    return this.http.get<RespuestaPaginada<ProductoFiltrado>>(this.API_URL, { params });
  }
}
