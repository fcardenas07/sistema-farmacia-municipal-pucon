export interface LoteBackendPayload {
  fechaElaboracion: string;
  fechaVencimiento: string;
  estado: string | null;
  numeroLote: string;
  cantidad: number;
  limiteMerma: number;
  porcentajeOferta: number | null;
  precioUnitario: number | null;
  idGuiaIngreso: string | null;

  codigo: {
    idProducto: string | null;
    codigoBarra: string;
    tipoCodigo: string | null;
    activo: boolean | null;
  };
}