package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

public class CodigoNoEncontradoException extends RuntimeException {
    public CodigoNoEncontradoException(String id) {
        super("No se encontró el código con id = " + id);
    }
}