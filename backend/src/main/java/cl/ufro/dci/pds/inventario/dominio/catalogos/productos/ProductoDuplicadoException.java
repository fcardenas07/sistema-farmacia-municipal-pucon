package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

public class ProductoDuplicadoException extends RuntimeException {
    public ProductoDuplicadoException(String id) {
        super("Ya existe un producto con id = " + id);
    }
}
