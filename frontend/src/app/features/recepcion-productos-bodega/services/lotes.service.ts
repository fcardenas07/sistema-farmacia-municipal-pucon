import { Injectable } from '@angular/core';
import { LoteInfo } from '../models/lote-info';

@Injectable({
  providedIn: 'root'
})
export class LotesService {

  private lotes: LoteInfo[] = [];

  setLotes(list: LoteInfo[]) {
    this.lotes = [...list];
  }

  getLotes(): LoteInfo[] {
    return [...this.lotes];
  }

  clear() {
    this.lotes = [];
  }
}
