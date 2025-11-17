package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

import java.util.List;

public record ProductoBuscado(
        String idProducto,
        String nombreComercial,
        String nombreGenerico,
        String presentacion,
        String dosificacion,
        String unidadMedida,
        boolean activo,
        List<CodigoBuscado> codigos
) {

    public static ProductoBuscado desde(Producto producto, List<Codigo> codigos) {
        return new ProductoBuscado(
                producto.getIdProducto(),
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                producto.getPresentacion(),
                producto.getDosificacion(),
                producto.getUnidadMedida(),
                producto.isActivo(),
                codigos.stream().map(CodigoBuscado::desde).toList()
        );
    }
}