package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

public record ProductoCreado(
        String idProducto,
        String nombreComercial,
        boolean activo,
        String urlFoto
) {
    public static ProductoCreado desde(Producto producto) {
        return new ProductoCreado(
                producto.getIdProducto(),
                producto.getNombreComercial(),
                producto.isActivo(),
                producto.getUrlFoto()
        );
    }
}