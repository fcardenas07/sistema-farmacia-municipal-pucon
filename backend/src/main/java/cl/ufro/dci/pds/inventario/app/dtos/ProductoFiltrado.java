package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

public record ProductoFiltrado(
        String idProducto,
        String nombreComercial,
        String nombreGenerico,
        boolean activo
) {
    public static ProductoFiltrado desde(Producto producto) {
        return new ProductoFiltrado(
                producto.getIdProducto(),
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                producto.getActivo()
        );
    }
}