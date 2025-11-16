package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

import cl.ufro.dci.pds.inventario.app.dtos.ProductoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioProducto {

    private final RepositorioProducto repositorioProducto;

    public ServicioProducto(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    public Producto crear(ProductoACrear dto) {
        if (repositorioProducto.existsById(dto.idProducto())) {
            throw new ProductoDuplicadoException(dto.idProducto());
        }

        var producto = dto.aEntidad();
        return repositorioProducto.save(producto);
    }

    public Producto actualizar(String id, ProductoAModificar dto) {
        var producto = repositorioProducto.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        dto.aplicarCambios(producto);
        return repositorioProducto.save(producto);
    }

    public List<Producto> buscarPorCampos(
            String nombreComercial,
            String nombreGenerico,
            Boolean activo
    ) {
        return repositorioProducto.buscarPorCampos(nombreComercial, nombreGenerico, activo);
    }
}