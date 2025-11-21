export interface ProductInfo {
  idProducto: string;                // Siempre llegará desde el dropdown (no puede ser null)
  nombreComercial: string;           // Nombre comercial
  nombreGenerico: string;            // Nombre genérico
  categoria: string;                 // Categoría
  imageUrl: string | null;           // Puede ser null
}
