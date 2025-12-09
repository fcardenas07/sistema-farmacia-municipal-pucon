import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RespuestaPaginadaQf } from '../models/respuesta-paginada-qf';
import { ProductoFiltradoQF } from '../models/producto-filtrado-qf';



@Injectable({
  providedIn: 'root'
})
export class ProductosStockCriticoService {

  private API_URL = 'http://localhost:8080/productos/buscar-stock';

  constructor(private http: HttpClient) {}

  buscarStockCritico(
    pagina: number,
    nombreComercial?: string,
    nombreGenerico?: string
  ): Observable<RespuestaPaginadaQf<ProductoFiltradoQF>> {

    let params = new HttpParams()
      .set('pagina', pagina)
      .set('tipoStock', 'CRITICO');

    if (nombreComercial && nombreComercial.trim() !== '') {
      params = params.set('nombreComercial', nombreComercial);
    }

    if (nombreGenerico && nombreGenerico.trim() !== '') {
      params = params.set('nombreGenerico', nombreGenerico);
    }

    return this.http.get<RespuestaPaginadaQf<ProductoFiltradoQF>>(this.API_URL, { params });
  }
}
