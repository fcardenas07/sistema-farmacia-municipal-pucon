package cl.ufro.dci.pds.inventario.infraestructura;

public interface ProyeccionProductoFiltrado {

    String getIdProducto();
    String getNombreComercial();
    String getNombreGenerico();
    String getNombreFabricante();
    Integer getDosificacion();
    String getUnidadMedida();
    Integer getStockTotal();
    Integer getStockMinimo();
    Integer getStockMaximo();

    String getUrlFoto();
}
