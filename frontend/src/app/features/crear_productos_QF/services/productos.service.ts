import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProductoCreacion } from '../models/producto-creacion';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductosService {

  private http = inject(HttpClient);
  private API_URL = 'http://localhost:8080/productos';

  crearProducto(producto: ProductoCreacion): Observable<any> {
    return this.http.post<any>(this.API_URL, producto);
  }

}
