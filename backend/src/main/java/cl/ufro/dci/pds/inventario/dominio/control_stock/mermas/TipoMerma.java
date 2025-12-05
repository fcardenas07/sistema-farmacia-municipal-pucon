package cl.ufro.dci.pds.inventario.dominio.control_stock.mermas;

public enum TipoMerma {

    PRODUCTO_DANIADO("Producto dañado"),
    RUPTURA_STOCK("Ruptura de stock"),
    ROBO_O_EXTRAVIO("Robo o extravío"),
    ERROR_INVENTARIO("Error de inventario"),
    RECALL_LABORATORIO("Recall del laboratorio");

    private final String descripcion;

    TipoMerma(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}