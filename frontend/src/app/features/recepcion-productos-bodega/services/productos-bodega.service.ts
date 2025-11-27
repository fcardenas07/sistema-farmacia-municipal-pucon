import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductoBackend } from '../models/producto-backend';
import { LoteBackendPayload } from '../models/lote-backend-payload';

@Injectable({
  providedIn: 'root'
})
export class ProductosBodegaService {

  private api = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // GET - Buscar productos desde backend
  buscarProductos(nombre: string): Observable<{ content: ProductoBackend[] }> {
    return this.http.get<{ content: ProductoBackend[] }>(
      `${this.api}/productos/buscar?nombreComercial=${nombre}`
    );
  }

  // POST - Enviar lote individual
  postInventario(payload: LoteBackendPayload): Observable<any> {
    return this.http.post(`${this.api}/inventario`, payload);
  }
}
