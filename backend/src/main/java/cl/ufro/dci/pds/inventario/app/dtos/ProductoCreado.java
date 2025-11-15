package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

public record ProductoCreado(
        String idProducto,
        String nombreComercial,
        String estado
) {
    // Mapper estático
    public static ProductoCreado desdeEntidad(Producto producto) {
        return new ProductoCreado(
                producto.getIdProducto(),
                producto.getNombreComercial(),
                producto.getEstado()
        );
    }
}