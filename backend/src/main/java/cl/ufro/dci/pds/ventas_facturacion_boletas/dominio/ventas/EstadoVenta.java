package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

public enum EstadoVenta {

    PENDIENTE_PAGO("Pendiente de pago"),
    APROBADA("Aprobada"),
    RECHAZADA("Rechazada");


    private final String descripcion;

    EstadoVenta(String descripcion) {
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