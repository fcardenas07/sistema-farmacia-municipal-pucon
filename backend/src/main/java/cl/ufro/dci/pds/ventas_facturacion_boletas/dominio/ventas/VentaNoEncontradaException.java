package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

public class VentaNoEncontradaException extends RuntimeException {
    public VentaNoEncontradaException(String id) {
        super("No se encontro venta con id: " + id);
    }
}
