import { MovimientoMerma } from './movimiento-merma';

export interface MovimientoMermaPage {
  content: MovimientoMerma[];
  totalPages: number;
  number: number;
  size: number;
}