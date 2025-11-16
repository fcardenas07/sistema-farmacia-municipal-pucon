package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

public class CodigoNoPerteneceProductoException extends RuntimeException {
    public CodigoNoPerteneceProductoException(String idCodigo, String idProducto) {
        super("El código con id = " + idCodigo + " no pertenece al producto con id = " + idProducto);
    }
}