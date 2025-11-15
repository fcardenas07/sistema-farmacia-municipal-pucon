package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

public class CodigoDuplicadoException extends RuntimeException {
    public CodigoDuplicadoException(String id) {
        super("Ya existe un codigo con id = " + id);
    }
}
