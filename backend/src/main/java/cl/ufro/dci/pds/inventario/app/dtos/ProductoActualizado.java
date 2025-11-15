package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

public record ProductoActualizado(
        String nombreComercial,
        String estado
) {
    public static ProductoActualizado desdeEntidad(Producto producto) {
        return new ProductoActualizado(
                producto.getNombreComercial(),
                producto.getEstado()
        );
    }
}