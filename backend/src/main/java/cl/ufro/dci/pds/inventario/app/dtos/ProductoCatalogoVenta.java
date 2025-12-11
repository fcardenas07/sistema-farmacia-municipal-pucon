package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.infraestructura.ProyeccionProductoVenta;

public record ProductoCatalogoVenta(String idProducto,
                                    String idLoteMasProximoAVencer,
                                    String nombreComercial,
                                    String nombreGenerico,
                                    String nombreFabricante,
                                    int dosificacion,
                                    String unidadMedida,
                                    int stockTotal,
                                    int precioVenta,
                                    String urlFoto) {
    public static ProductoCatalogoVenta desdeProyeccion(ProyeccionProductoVenta p) {
        return new ProductoCatalogoVenta(
                p.getIdProducto(),
                p.getIdLoteMasProximoAVencer(),
                p.getNombreComercial(),
                p.getNombreGenerico(),
                p.getNombreFabricante(),
                p.getDosificacion() != null ? p.getDosificacion() : 0,
                p.getUnidadMedida(),
                p.getStockTotal() != null ? p.getStockTotal() : 0,
                p.getPrecioVenta() != null ? p.getPrecioVenta() : 0,
                p.getUrlFoto()
        );
    }
}
