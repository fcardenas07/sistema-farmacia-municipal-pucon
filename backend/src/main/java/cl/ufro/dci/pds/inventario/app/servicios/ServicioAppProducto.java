package cl.ufro.dci.pds.inventario.app.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.ServicioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.fabricantes.ServicioFabricante;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ProductoNoEncontradoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ServicioProducto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.ServicioMovimiento;
import cl.ufro.dci.pds.inventario.infraestructura.RepositorioConsultaProducto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;

@Service
public class ServicioAppProducto {

    private final ServicioProducto servicioProducto;
    private final ServicioCodigo servicioCodigo;
    private final ServicioLote servicioLote;
    private final ServicioMovimiento servicioMovimiento;
    private final ServicioFabricante servicioFabricante;
    private final RepositorioConsultaProducto repositorioConsultaProducto;

    public ServicioAppProducto(ServicioProducto servicioProducto,
                               ServicioCodigo servicioCodigo,
                               ServicioLote servicioLote,
                               ServicioMovimiento servicioMovimiento,
                               ServicioFabricante servicioFabricante,
                               RepositorioConsultaProducto repositorioConsultaProducto) {
        this.servicioProducto = servicioProducto;
        this.servicioCodigo = servicioCodigo;
        this.servicioLote = servicioLote;
        this.servicioMovimiento = servicioMovimiento;
        this.servicioFabricante = servicioFabricante;
        this.repositorioConsultaProducto = repositorioConsultaProducto;
    }

    @Transactional
    public ProductoCreado crearProducto(ProductoACrear dto) {

        var producto = dto.aEntidad();

        if (dto.idFabricante() != null) {
            var fabricante = servicioFabricante.obtenerPorId(dto.idFabricante());
            producto.setFabricante(fabricante);
        }

        var creado = servicioProducto.validarYGuardar(producto);
        return ProductoCreado.desde(creado);
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
    public void actualizarFoto(String id, MultipartFile foto) {
        servicioProducto.guardarFoto(id, foto);
    }

    @Transactional
    public ProductoDetalle obtenerProductoPorId(String idProducto) {
        var proyeccion = repositorioConsultaProducto
                .obtenerDetalleProducto(idProducto);

        if (proyeccion == null) {
            throw new ProductoNoEncontradoException(idProducto);
        }

        var codigos = servicioCodigo.obtenerCodigosConIdProducto(idProducto);
        return ProductoDetalle.desdeProyeccion(proyeccion, codigos);
    }

    @Transactional
    public List<ProductoSimple> buscarProductosSimples(String nombreComercial) {
        var productos = repositorioConsultaProducto.buscarProductosSimples(nombreComercial);

        return productos.stream()
                .map(ProductoSimple::desdeProyeccion)
                .toList();
    }

    @Transactional
    public Page<ProductoFiltrado> buscarProductosFiltrados(
            String nombreComercial,
            String nombreGenerico,
            CategoriaProducto categoria,
            int numeroPagina,
            int limite,
            ProductoFiltrado.FiltroStock filtroStock
    ) {
        var proyecciones = repositorioConsultaProducto.buscarProductosConStock(
                nombreComercial,
                nombreGenerico,
                categoria
        );

        var filtradosOrdenados = proyecciones.stream()
                .map(ProductoFiltrado::desdeProyeccion)
                .filter(p -> filtrarPorEstado(p, filtroStock))
                .sorted(Comparator.comparingInt(p -> p.estadoStock().getPrioridad()))
                .toList();

        var pageable = PageRequest.of(numeroPagina, limite);

        return construirPaginaFiltrados(filtradosOrdenados, pageable);
    }

    private Page<ProductoFiltrado> construirPaginaFiltrados(
            List<ProductoFiltrado> filtradosOrdenados,
            Pageable pageable
    ) {
        var fromIndex = (int) pageable.getOffset();
        var toIndex = Math.min(fromIndex + pageable.getPageSize(), filtradosOrdenados.size());

        List<ProductoFiltrado> pagina;
        if (fromIndex >= filtradosOrdenados.size()) {
            pagina = List.of();
        } else {
            pagina = filtradosOrdenados.subList(fromIndex, toIndex);
        }

        return new PageImpl<>(pagina, pageable, filtradosOrdenados.size());
    }

    private boolean filtrarPorEstado(ProductoFiltrado producto, ProductoFiltrado.FiltroStock filtro) {
        return filtro == ProductoFiltrado.FiltroStock.NORMAL
                ? producto.estadoStock() == ProductoFiltrado.EstadoStock.NORMAL
                : producto.estadoStock() != ProductoFiltrado.EstadoStock.NORMAL;
    }

    @Transactional
    public void darBajaProducto(String idProducto) {
        servicioProducto.darBaja(idProducto);

        var codigos = servicioCodigo.obtenerCodigosConIdProducto(idProducto);
        codigos.forEach(servicioCodigo::darBaja);

        var idsCodigos = codigos.stream().map(Codigo::getIdCodigo).toList();
        var lotes = servicioLote.obtenerLotesDeCodigos(idsCodigos);

        darBajaLotesYStocks(lotes);
    }

    private void darBajaLotesYStocks(List<Lote> lotes) {
        for (var lote : lotes) {
            var cantidadBajada = servicioLote.darBaja(lote);

            servicioMovimiento.registrarMovimientoPorBajaProducto(
                    lote.getCodigo().getProducto(),
                    lote,
                    cantidadBajada
            );
        }
    }
}