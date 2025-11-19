package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

import java.util.List;

public record ProductoCreado(
        String idProducto,
        String nombreComercial,
        boolean activo,
        String urlFoto,
        List<CodigoCreado> codigos
) {
    public static ProductoCreado desde(Producto producto, List<Codigo> codigos) {
        return new ProductoCreado(
                producto.getIdProducto(),
                producto.getNombreComercial(),
                producto.isActivo(),
                producto.getUrlFoto(),
                codigos.stream().map(CodigoCreado::desde).toList()
        );
    }
}