package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

public record ProductoParaCodigo(
        String idProducto,
        String nombreComercial,
        String nombreFabricante,
        String urlFoto
) {

    public static ProductoParaCodigo desde(Producto producto) {
        var fabricante = producto.getFabricante();
        var contacto = fabricante != null ? fabricante.getContacto() : null;
        var nombreFabricante = contacto != null ? contacto.getNombre() : null;

        return new ProductoParaCodigo(
                producto.getIdProducto(),
                producto.getNombreComercial(),
                nombreFabricante,
                producto.getUrlFoto()
        );
    }
}