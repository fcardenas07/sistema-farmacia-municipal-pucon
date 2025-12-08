package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos;

public enum TipoPago {

    TARJETA_CREDITO("Tarjeta de credito"),
    EFECTIVO("Efectivo"),
    TARJETA_DEBITO("Tarjeta debito");

    private final String descripcion;

    TipoPago(String descripcion) {
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
