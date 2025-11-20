package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

public class LoteNoEncontradoException extends RuntimeException {
  public LoteNoEncontradoException(String id) {
    super("No se encontró lote con id = " + id);
  }
}
