package cl.ufro.dci.pds.inventario.app.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.ProductoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoCreado;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoModificado;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.ServicioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ServicioProducto;
import org.springframework.stereotype.Service;

@Service
public class ServicioAppProducto {
    private final ServicioProducto servicioProducto;
    private final ServicioCodigo servicioCodigo;

    public ServicioAppProducto(ServicioProducto servicioProducto, ServicioCodigo servicioCodigo) {
        this.servicioProducto = servicioProducto;
        this.servicioCodigo = servicioCodigo;
    }

    public ProductoCreado crearProducto(ProductoACrear dto) {
        var creado = servicioProducto.crear(dto);
        var codigos = servicioCodigo.obtenerCodigosConIdProducto(dto.idProducto());
        return ProductoCreado.desde(creado, codigos);
    }

    public ProductoModificado actualizarProducto(String id, ProductoAModificar dto) {
        var actualizado = servicioProducto.actualizar(id, dto);
        var codigos = servicioCodigo.obtenerCodigosConIdProducto(id);
        return ProductoModificado.desde(actualizado, codigos);
    }
}
