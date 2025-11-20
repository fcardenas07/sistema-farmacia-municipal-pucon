package cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos;

public enum TipoMovimiento {
    INGRESO("Ingreso de stock"),
    BAJA("Baja de producto"),
    MERMA("Merma"),
    VENTA("Venta"),
    DEVOLUCION("Devolución"),
    FRACCIONAMIENTO("Fraccionamiento");

    private final String nombreLegible;

    TipoMovimiento(String nombreLegible) {
        this.nombreLegible = nombreLegible;
    }

    public String getNombreLegible() {
        return nombreLegible;
    }
}
