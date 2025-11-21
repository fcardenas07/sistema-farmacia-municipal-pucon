package cl.ufro.dci.pds.inventario.dominio.catalogos.fabricantes;

public class FabricanteNoEncontradoException extends RuntimeException {
  public FabricanteNoEncontradoException(String id) {
    super("No se encontró fabricante con id = " + id);
  }
}
