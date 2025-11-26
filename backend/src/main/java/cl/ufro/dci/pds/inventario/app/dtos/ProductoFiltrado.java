package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

public record ProductoFiltrado(
        String idProducto,
        String nombreComercial,
        String nombreGenerico,
        String nombreFabricante,
        int dosificacion,
        String unidadMedida,
        int stockTotal,
        String urlFoto,
        EstadoStock estadoStock,
        String estadoStockTitulo
) {

    public static ProductoFiltrado desde(Producto producto, int stockTotal) {
        var nombreFabricante = producto.getFabricante() != null && producto.getFabricante().getContacto() != null
                ? producto.getFabricante().getContacto().getNombre()
                : null;

        var estadoStock = calcularEstadoStock(stockTotal, producto.getStockMinimo(), producto.getStockMaximo());

        return new ProductoFiltrado(
                producto.getIdProducto(),
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                nombreFabricante,
                producto.getDosificacion(),
                producto.getUnidadMedida(),
                stockTotal,
                producto.getUrlFoto(),
                estadoStock,
                estadoStock.getTitulo()
        );
    }

    private static EstadoStock calcularEstadoStock(int stockActual, int stockMinimo, int stockMaximo) {
        if (stockMaximo <= stockMinimo) return EstadoStock.NORMAL;

        double porcentaje = (double) (stockActual - stockMinimo) / (stockMaximo - stockMinimo) * 100;
        if (porcentaje < 0) porcentaje = 0;

        if (porcentaje <= 20) return EstadoStock.MUY_BAJO;
        if (porcentaje <= 40) return EstadoStock.BAJO;
        if (porcentaje <= 59) return EstadoStock.MEDIO;
        return EstadoStock.NORMAL;
    }

    public enum EstadoStock {
        MUY_BAJO("Muy Bajo", 1),
        BAJO("Bajo", 2),
        MEDIO("Medio", 3),
        NORMAL("Normal", 4);

        private final String titulo;
        private final int prioridad;

        EstadoStock(String titulo, int prioridad) {
            this.titulo = titulo;
            this.prioridad = prioridad;
        }

        public String getTitulo() {
            return titulo;
        }

        public int getPrioridad() {
            return prioridad;
        }
    }

    public enum FiltroStock {NORMAL, CRITICO}
}
