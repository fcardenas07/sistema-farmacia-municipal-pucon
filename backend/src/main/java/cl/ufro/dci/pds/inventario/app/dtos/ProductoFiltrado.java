package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

public record ProductoFiltrado(
        String idProducto,
        String nombreComercial,
        String nombreGenerico,
        String categoria,
        boolean activo,
        int stockTotal,
        String urlFoto
) {

    public boolean isDisponible() {
        return stockTotal > 0 && activo;
    }

    public static ProductoFiltrado desde(Producto producto, int stockTotal) {
        return new ProductoFiltrado(
                producto.getIdProducto(),
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                producto.getCategoria(),
                producto.isActivo(),
                stockTotal,
                producto.getUrlFoto()
        );
    }
}
