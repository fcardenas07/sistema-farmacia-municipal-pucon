package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

public class CodigoNoEncontradoException extends RuntimeException {
  public CodigoNoEncontradoException(String id) {
    super("No se encontró código con id = " + id);
  }
}
