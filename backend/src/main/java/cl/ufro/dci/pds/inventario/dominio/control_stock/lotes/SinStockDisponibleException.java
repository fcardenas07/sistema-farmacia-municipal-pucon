package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

public class SinStockDisponibleException extends RuntimeException {
    public SinStockDisponibleException(String numeroLote) {
      super("El lote "+ numeroLote + " no tiene stock disponible");
    }
}
