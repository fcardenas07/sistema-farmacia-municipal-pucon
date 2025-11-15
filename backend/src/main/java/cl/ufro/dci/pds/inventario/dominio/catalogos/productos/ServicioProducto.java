package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

import cl.ufro.dci.pds.inventario.app.dtos.ProductoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.ServicioCodigo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ServicioProducto {

    private final RepositorioProducto repositorioProducto;
    private final ServicioCodigo servicioCodigo;

    public ServicioProducto(RepositorioProducto repositorioProducto, ServicioCodigo servicioCodigo) {
        this.repositorioProducto = repositorioProducto;
        this.servicioCodigo = servicioCodigo;
    }

    @Transactional
    public Producto crear(ProductoACrear dto) {
        if (repositorioProducto.existsById(dto.idProducto())) {
            throw new ProductoDuplicadoException(dto.idProducto());
        }

        var producto = dto.aEntidad();
        agregarCodigosAlProducto(producto, dto);
        return repositorioProducto.save(producto);
    }

    private void agregarCodigosAlProducto(Producto producto, ProductoACrear dto) {
        if (dto.codigos() == null) return;
        dto.codigos().forEach((codigo) -> servicioCodigo.crear(producto, codigo));
    }

    @Transactional
    public Producto actualizar(String id, ProductoAModificar dto) {
        var producto = repositorioProducto.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        dto.aplicarCambios(producto);
        actualizarCodigosDelProducto(id, dto);
        return repositorioProducto.save(producto);
    }

    private void actualizarCodigosDelProducto(String idProducto, ProductoAModificar dto) {
        if (dto.codigos() == null) return;
        dto.codigos().forEach(c -> servicioCodigo.actualizarParaProducto(idProducto, c));
    }
}