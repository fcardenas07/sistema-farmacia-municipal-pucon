package cl.ufro.dci.pds.inventario.infraestructura;

public interface ProyeccionProductoVenta {
    String getIdProducto();
    String getIdLoteMasProximoAVencer();
    String getNombreComercial();
    String getNombreGenerico();
    String getNombreFabricante();

    Integer getDosificacion();
    String getUnidadMedida();

    Integer getStockTotal();

    Integer getPrecioVenta();

    String getUrlFoto();
}
