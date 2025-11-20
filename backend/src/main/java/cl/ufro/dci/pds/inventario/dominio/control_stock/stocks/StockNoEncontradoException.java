package cl.ufro.dci.pds.inventario.dominio.control_stock.stocks;

public class StockNoEncontradoException extends RuntimeException {
  public StockNoEncontradoException(String id) {
    super("No se encontró stock con id = " + id);
  }
}
