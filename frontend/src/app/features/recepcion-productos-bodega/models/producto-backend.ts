export interface ProductoBackend {
  idProducto: string;
  nombreComercial: string;
  nombreGenerico: string;
  categoria: string;
  activo: boolean;
  stockTotal: number;
  urlFoto: string | null;
  disponible: boolean;
}
