import { ProductInfo } from "./product-info";


export interface LoteInfo {
  numeroLote: string;
  fechaElaboracion: string;
  fechaVencimiento: string;
  cantidad: number;
  limiteMerma: number;
  precioUnitario: number;
  codigoBarra: string;
  product: ProductInfo;  // Producto seleccionado con Opción A
}


