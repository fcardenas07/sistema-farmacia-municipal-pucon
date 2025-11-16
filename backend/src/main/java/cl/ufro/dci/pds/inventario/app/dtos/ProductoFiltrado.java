package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

public record ProductoFiltrado(
        String idProducto,
        String nombreComercial,
        String nombreGenerico,
        boolean activo,
        int stockTotal
) {

    public boolean isDisponible() {
        return stockTotal > 0 && activo;
    }

    public static ProductoFiltrado desde(Producto producto, int stockTotal) {
        return new ProductoFiltrado(
                producto.getIdProducto(),
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                producto.getActivo(),
                stockTotal
        );
    }
}
