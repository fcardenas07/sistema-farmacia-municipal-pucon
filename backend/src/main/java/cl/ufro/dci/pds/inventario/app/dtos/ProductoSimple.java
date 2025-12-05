package cl.ufro.dci.pds.inventario.app.dtos;


import cl.ufro.dci.pds.inventario.infraestructura.ProyeccionProductoSimple;

public record ProductoSimple(
        String idProducto,
        String nombreComercial,
        String nombreFabricante,
        String urlFoto
) {

    public static ProductoSimple desdeProyeccion(ProyeccionProductoSimple p) {
        return new ProductoSimple(
                p.getIdProducto(),
                p.getNombreComercial(),
                p.getNombreFabricante(),
                p.getUrlFoto()
        );
    }
}