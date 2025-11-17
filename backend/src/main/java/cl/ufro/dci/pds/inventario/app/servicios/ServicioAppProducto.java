package cl.ufro.dci.pds.inventario.app.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.ServicioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ServicioProducto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServicioAppProducto {
    private final ServicioProducto servicioProducto;
    private final ServicioCodigo servicioCodigo;
    private final ServicioLote servicioLote;

    public ServicioAppProducto(ServicioProducto servicioProducto, ServicioCodigo servicioCodigo, ServicioLote servicioLote) {
        this.servicioProducto = servicioProducto;
        this.servicioCodigo = servicioCodigo;
        this.servicioLote = servicioLote;
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
    public void actualizarFoto(String id, MultipartFile foto) {
        servicioProducto.guardarFoto(id, foto);
    }

    @Transactional
    public ProductoBuscado obtenerProductoPorId(String id) {
        var producto = servicioProducto.obtenerPorId(id);
        var codigos = servicioCodigo.obtenerCodigosConIdProducto(id);
        int stockTotal = calcularStockTotal(id);

        return ProductoBuscado.desde(producto, codigos, stockTotal);
    }

    private int calcularStockTotal(String idProducto) {
        var lotes = servicioLote.obtenerLotesDeProductos(List.of(idProducto));
        return lotes.stream()
                .mapToInt(l -> l.getStock() != null ? l.getStock().getCantidadActual() : 0)
                .sum();
    }

    @Transactional
    public List<ProductoFiltrado> buscarProductosFiltrados(
            String nombreComercial,
            String nombreGenerico,
            Boolean activo
    ) {
        var productos = servicioProducto.buscarPorCampos(nombreComercial, nombreGenerico, activo);

        var ids = productos.stream()
                .map(Producto::getIdProducto)
                .toList();

        var stockPorProducto = agruparStockPorProducto(ids);

        return productos.stream()
                .map(p -> mapearProductoFiltrado(p, stockPorProducto))
                .toList();
    }

    private Map<String, Integer> agruparStockPorProducto(List<String> ids) {
        var lotes = servicioLote.obtenerLotesDeProductos(ids);
        return lotes.stream()
                .collect(Collectors.groupingBy(
                        l -> l.getProducto().getIdProducto(),
                        Collectors.summingInt(this::stockActual)
                ));
    }

    private int stockActual(Lote lote) {
        var stock = lote.getStock();
        return (stock != null) ? stock.getCantidadActual() : 0;
    }

    private ProductoFiltrado mapearProductoFiltrado(Producto p, Map<String, Integer> stockPorProducto) {
        int stockTotal = stockPorProducto.getOrDefault(p.getIdProducto(), 0);
        return ProductoFiltrado.desde(p, stockTotal);
    }
}
