export interface ProductoFiltrado {
  idProducto: string;
  nombreComercial: string;
  nombreGenerico: string;
  nombreFabricante: string | null;
  dosificacion: number;
  unidadMedida: string;
  stockTotal: number;
  urlFoto: string | null;
  estadoStock: string;
  estadoStockTitulo: string;
}
