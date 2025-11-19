package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

import java.util.List;

public record ProductoModificado(
        String nombreComercial,
        boolean activo,
        String urlFoto,
        List<CodigoModificado> codigos
) {
    public static ProductoModificado desde(Producto producto, List<Codigo> codigos) {
        return new ProductoModificado(
                producto.getNombreComercial(),
                producto.isActivo(),
                producto.getUrlFoto(),
                codigos.stream().map(CodigoModificado::desde).toList()
        );
    }
}