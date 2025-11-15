package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

public class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(String id) {
        super("No se encontró producto con id = " + id);
    }
}