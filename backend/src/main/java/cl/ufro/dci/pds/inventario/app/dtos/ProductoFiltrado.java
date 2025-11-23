package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

public record ProductoFiltrado(
        String idProducto,
        String nombreComercial,
        int dosificacion,
        String unidadMedida,
        String categoria,
        int stockTotal,
        String urlFoto,
        String nivelStock
) {

    public static ProductoFiltrado desde(Producto producto, int stockTotal) {
        return new ProductoFiltrado(
                producto.getIdProducto(),
                producto.getNombreComercial(),
                producto.getDosificacion(),
                producto.getUnidadMedida(),
                producto.getCategoria(),
                stockTotal,
                producto.getUrlFoto(),
                calcularNivelStock(stockTotal, producto.getStockMinimo(), producto.getStockMaximo()).getTitulo()
        );
    }

    private static NivelStock calcularNivelStock(int stockActual, int stockMinimo, int stockMaximo) {
        if (stockMaximo <= stockMinimo) return NivelStock.NORMAL;

        double porcentaje = (double)(stockActual - stockMinimo) / (stockMaximo - stockMinimo) * 100;
        if (porcentaje < 0) porcentaje = 0;

        if (porcentaje <= 20) return NivelStock.MUY_BAJO;
        else if (porcentaje <= 40) return NivelStock.BAJO;
        else if (porcentaje <= 59) return NivelStock.MEDIO;
        else return NivelStock.NORMAL;
    }

    private enum NivelStock {
        MUY_BAJO("Muy Bajo"),
        BAJO("Bajo"),
        MEDIO("Medio"),
        NORMAL("Normal");

        private final String titulo;

        NivelStock(String titulo) {
            this.titulo = titulo;
        }

        public String getTitulo() {
            return titulo;
        }

        @Override
        public String toString() {
            return titulo;
        }
    }
}
