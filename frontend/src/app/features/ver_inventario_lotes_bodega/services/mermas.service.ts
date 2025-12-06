import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MermaRequest } from '../models/merma-request';

@Injectable({ providedIn: 'root' })
export class MermasService {

  private apiUrl = 'http://localhost:8080/inventario/mermas';

  constructor(private http: HttpClient) {}

  registrarMerma(body: MermaRequest): Observable<string> {
    return this.http.post<string>(this.apiUrl, body, {
      responseType: 'text' as 'json'   // 👈 necesario para que no falle
    });
  }
}
