package cl.ufro.dci.pds.inventario.infraestructura;

public interface ProyeccionProductoDetalle {
    String getIdProducto();
    String getNombreComercial();
    String getNombreGenerico();
    String getPresentacion();
    String getNombreFabricante();
    Integer getDosificacion();
    String getUnidadMedida();
    Boolean getActivo();
    Integer getStockMinimo();
    Integer getStockMaximo();
    String getUrlFoto();
    Integer getStockTotal();
}
