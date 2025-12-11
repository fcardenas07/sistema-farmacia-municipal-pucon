import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ProductoBackend } from '../models/producto-backend';
import { LoteBackendPayload } from '../models/lote-backend-payload';
import { LoteInfo } from '../models/lote-info';

@Injectable({
  providedIn: 'root'
})
export class ProductosBodegaService {

  private api = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // ================================
  // BUSCAR PRODUCTOS
  // ================================
  buscarProductos(nombre: string): Observable<ProductoBackend[]> {
    return this.http.get<ProductoBackend[]>(
      `${this.api}/productos/opciones?nombreComercial=${nombre}`
    );
  }

  // ================================
  // CONSTRUIR PAYLOAD PARA BACKEND
  // ================================
  private construirPayload(lote: LoteInfo): LoteBackendPayload {
    return {
      fechaElaboracion: `${lote.fechaElaboracion}T00:00:00`,
      fechaVencimiento: `${lote.fechaVencimiento}T00:00:00`,
      estado: "ACTIVO",
      numeroLote: lote.numeroLote,
      cantidad: lote.cantidad,
      limiteMerma: lote.limiteMerma,
      porcentajeOferta: 0.10,
      precioUnitario: lote.precioUnitario,
      idGuiaIngreso: null,

      codigo: {
        idProducto: lote.product.idProducto,
        codigoBarra: lote.codigoBarra,
        tipoCodigo: "EAN",
        activo: true
      }
    };
  }

  // ================================
  // POST USANDO PAYLOAD COMPLETO
  // ================================
  postInventario(payload: LoteBackendPayload): Observable<any> {

    console.log("📤 Payload ENVIADO MANUALMENTE al backend:", payload);

    return this.http.post(`${this.api}/inventario`, payload);
  }

  // ================================
  // POST USANDO LoteInfo → Payload
  // ================================
  postInventarioFromLote(lote: LoteInfo): Observable<any> {
    const payload = this.construirPayload(lote);

    // 🔎 ESTE ES EL QUE QUEREMOS VER
    console.log("📤 Payload ENVIADO (FromLote) al backend:", payload);

    return this.http.post(`${this.api}/inventario`, payload);
  }
}
