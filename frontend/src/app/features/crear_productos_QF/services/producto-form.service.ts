import { Injectable } from '@angular/core';
import { ProductoCreacion } from '../models/producto-creacion';

@Injectable({
  providedIn: 'root'
})
export class ProductoFormService {

  private draft: ProductoCreacion | null = null;

  setDraft(data: ProductoCreacion) {
    this.draft = data;
  }

  getDraft(): ProductoCreacion | null {
    return this.draft;
  }

  clearDraft() {
    this.draft = null;
  }

}
