package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.facturacion.boletas;

public class BoletaNoEncontradaException extends RuntimeException {
    public BoletaNoEncontradaException(String id) {
        super("No se encontró boleta con id = " + id);
    }
}
