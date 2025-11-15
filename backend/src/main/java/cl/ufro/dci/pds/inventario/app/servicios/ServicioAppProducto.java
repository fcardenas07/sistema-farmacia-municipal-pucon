package cl.ufro.dci.pds.inventario.app.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.NuevoProducto;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoActualizado;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoCreado;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoModificado;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ServicioProducto;
import org.springframework.stereotype.Service;

@Service
public class ServicioAppProducto {

    private final ServicioProducto servicio;

    public ServicioAppProducto(ServicioProducto servicio) {
        this.servicio = servicio;
    }

    public ProductoCreado crearProducto(NuevoProducto dto) {
        Producto creado = servicio.crear(dto);
        return ProductoCreado.desdeEntidad(creado);
    }

    public ProductoActualizado actualizarProducto(String id, ProductoModificado dto) {
        Producto actualizado = servicio.actualizar(id, dto);
            return ProductoActualizado.desdeEntidad(actualizado);
    }
}
