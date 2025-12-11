import { IngresoLote } from "./ingreso-lote";

export interface IngresoPage {
  content: IngresoLote[];
  totalPages: number;
  number: number; // página actual
  size: number;
}
