package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos;

public class EstrategiaPagoNoEncontradaException extends RuntimeException {
    public EstrategiaPagoNoEncontradaException(String tipo) {
        super("No se encontró estrategia de pago para tipo: " + tipo);
    }
}
