package cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos;

public class MovimientoNoEncontradoException extends RuntimeException {
    public MovimientoNoEncontradoException(String id) {
        super("No se encontro movimiento con id: " + id);
    }
}
