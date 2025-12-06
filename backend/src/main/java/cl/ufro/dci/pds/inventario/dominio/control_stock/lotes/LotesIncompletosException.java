package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

public class LotesIncompletosException extends RuntimeException {
    public LotesIncompletosException(String message) {
        super(message);
    }
}
