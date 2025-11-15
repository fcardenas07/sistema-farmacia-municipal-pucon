package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

public record CodigoACrear(
        String idCodigo,
        String codigoBarra,
        String tipoCodigo,
        boolean activo
) {
    public Codigo aEntidad(Producto producto) {
        Codigo c = new Codigo();
        c.setProducto(producto);
        c.setIdCodigo(idCodigo);
        c.setCodigoBarra(codigoBarra);
        c.setTipoCodigo(tipoCodigo);
        c.setActivo(activo);
        return c;
    }
}