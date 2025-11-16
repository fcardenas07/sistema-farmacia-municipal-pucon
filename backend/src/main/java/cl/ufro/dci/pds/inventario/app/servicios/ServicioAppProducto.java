package cl.ufro.dci.pds.inventario.app.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.ServicioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ServicioProducto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioAppProducto {
    private final ServicioProducto servicioProducto;
    private final ServicioCodigo servicioCodigo;

    public ServicioAppProducto(ServicioProducto servicioProducto, ServicioCodigo servicioCodigo) {
        this.servicioProducto = servicioProducto;
        this.servicioCodigo = servicioCodigo;
    }

    @Transactional
    public ProductoCreado crearProducto(ProductoACrear dto) {
        var creado = servicioProducto.crear(dto);

        if (dto.codigos() != null) {
            dto.codigos().forEach(c -> servicioCodigo.crear(creado, c));
        }

        var codigos = servicioCodigo.obtenerCodigosConIdProducto(creado.getIdProducto());
        return ProductoCreado.desde(creado, codigos);
    }

    @Transactional
    public ProductoModificado actualizarProducto(String id, ProductoAModificar dto) {
        var actualizado = servicioProducto.actualizar(id, dto);

        if (dto.codigos() != null) {
            dto.codigos().forEach(c -> servicioCodigo.actualizarParaProducto(id, c));
        }

        var codigos = servicioCodigo.obtenerCodigosConIdProducto(id);
        return ProductoModificado.desde(actualizado, codigos);
    }

    @Transactional
    public List<ProductoFiltrado> buscarProductosFiltrados(
            String idProducto,
            String nombreComercial,
            String nombreGenerico,
            Boolean activo
    ) {
        var productos = servicioProducto.buscarPorCampos(idProducto, nombreComercial, nombreGenerico, activo);

        return productos.stream()
                .map(ProductoFiltrado::desde)
                .toList();
    }
}
