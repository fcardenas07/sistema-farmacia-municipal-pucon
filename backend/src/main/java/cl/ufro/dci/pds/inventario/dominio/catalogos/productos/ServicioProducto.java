package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

import cl.ufro.dci.pds.infraestructura.ServicioAlmacenamientoImagen;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ServicioProducto {

    private final RepositorioProducto repositorioProducto;
    private final ServicioAlmacenamientoImagen servicioAlmacenamientoImagen;

    private static final String CARPETA_PRODUCTO = "productos";
    private static final String PREFIJO_PRODUCTO = "P";

    public ServicioProducto(RepositorioProducto repositorioProducto, ServicioAlmacenamientoImagen servicioAlmacenamientoImagen) {
        this.repositorioProducto = repositorioProducto;
        this.servicioAlmacenamientoImagen = servicioAlmacenamientoImagen;
    }

    public Producto validarYGuardar(Producto producto) {
        var idFabricante = producto.getFabricante() != null ? producto.getFabricante().getIdFabricante() : null;

        var existe = repositorioProducto.existsByClaveUnica(
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                producto.getPresentacion(),
                producto.getDosificacion(),
                producto.getUnidadMedida(),
                idFabricante
        );

        if (existe) {
            throw new ProductoDuplicadoException();
        }

        return repositorioProducto.save(producto);
    }

    public Producto actualizar(String id, ProductoAModificar dto) {
        var producto = repositorioProducto.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        dto.aplicarCambios(producto);
        return repositorioProducto.save(producto);
    }

    public Producto obtenerPorId(String id) {
        return repositorioProducto.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    public void guardarFoto(String idProducto, MultipartFile foto) {
        if (foto == null || foto.isEmpty()) return;

        var producto = obtenerPorId(idProducto);
        String urlFoto = servicioAlmacenamientoImagen.guardarFoto(foto, CARPETA_PRODUCTO, PREFIJO_PRODUCTO);
        producto.setUrlFoto(urlFoto);
        repositorioProducto.save(producto);
    }

    public Page<Producto> buscarPorCampos(
            String nombreComercial,
            String nombreGenerico,
            Boolean activo,
            CategoriaProducto categoria,
            int numeroPagina
    ) {
        var pageable = PageRequest.of(numeroPagina, 4);
        return repositorioProducto.buscarPorCampos(nombreComercial, nombreGenerico, activo, categoria, pageable);
    }

    public List<Producto> buscarPorCampos(
            String nombreComercial,
            String nombreGenerico,
            Boolean activo,
            CategoriaProducto categoria
    ) {
        return repositorioProducto.buscarPorCampos(nombreComercial, nombreGenerico, activo, categoria);
    }

    public void darBaja(String id) {
        var producto = obtenerPorId(id);
        producto.setActivo(false);
        repositorioProducto.save(producto);
    }
}