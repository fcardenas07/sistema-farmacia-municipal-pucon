package cl.ufro.dci.pds.inventario.app.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.ServicioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.fabricantes.Fabricante;
import cl.ufro.dci.pds.inventario.dominio.catalogos.fabricantes.ServicioFabricante;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ServicioProducto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.ServicioMovimiento;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.ServicioStock;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private final ServicioStock servicioStock;
    private final ServicioMovimiento servicioMovimiento;
    private final ServicioFabricante servicioFabricante;

    public ServicioAppProducto(ServicioProducto servicioProducto,
                               ServicioCodigo servicioCodigo,
                               ServicioLote servicioLote,
                               ServicioStock servicioStock,
                               ServicioMovimiento servicioMovimiento,
                               ServicioFabricante servicioFabricante) {
        this.servicioProducto = servicioProducto;
        this.servicioCodigo = servicioCodigo;
        this.servicioLote = servicioLote;
        this.servicioStock = servicioStock;
        this.servicioMovimiento = servicioMovimiento;
        this.servicioFabricante = servicioFabricante;
    }

    @Transactional
    public ProductoCreado crearProducto(ProductoACrear dto) {
        Fabricante fabricante = null;
        if (dto.idFabricante() != null) {
            fabricante = servicioFabricante.obtenerPorId(dto.idFabricante());
        }

        var producto = dto.aEntidad();

        if (fabricante != null) {
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
    public ProductoBuscado obtenerProductoPorId(String idProducto) {
        var producto = servicioProducto.obtenerPorId(idProducto);
        var codigos = servicioCodigo.obtenerCodigosConIdProducto(idProducto);
        int stockTotal = calcularStockTotal(codigos);

        return ProductoBuscado.desde(producto, codigos, stockTotal);
    }

    @Transactional
    public List<ProductoParaCodigo> buscarProductosParaCodigo(String nombreComercial) {
        var productos = servicioProducto.buscarPorCampos(
                nombreComercial, null, null, null
        );

        return productos.stream()
                .map(ProductoParaCodigo::desde)
                .toList();
    }

    @Transactional
    public Page<ProductoFiltrado> buscarProductosFiltrados(
            String nombreComercial,
            String nombreGenerico,
            Boolean activo,
            CategoriaProducto categoria,
            int numeroPagina
    ) {
        var productosPage = servicioProducto.buscarPorCampos(
                nombreComercial, nombreGenerico, activo, categoria, numeroPagina
        );

        var idsProducto = productosPage.getContent().stream()
                .map(Producto::getIdProducto)
                .toList();

        var codigosPorProducto = mapearCodigosPorProducto(idsProducto);
        var lotes = obtenerLotesDeCodigos(codigosPorProducto.keySet().stream().toList());
        var stockPorProducto = agruparStockPorProducto(lotes, codigosPorProducto);

        var filtrados = productosPage.getContent().stream()
                .map(p -> {
                    int stockTotal = stockPorProducto.getOrDefault(p.getIdProducto(), 0);
                    return ProductoFiltrado.desde(p, stockTotal);
                })
                .toList();

        return new PageImpl<>(filtrados, productosPage.getPageable(), productosPage.getTotalElements());
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
            var stock = lote.getStock();
            var cantidadBajada = 0;

            if (stock != null) {
                cantidadBajada = servicioStock.darBaja(stock);
            }
            servicioLote.darBaja(lote);

            servicioMovimiento.registrarMovimientoPorBajaProducto(
                    lote.getCodigo().getProducto(),
                    lote,
                    cantidadBajada
            );
        }
    }

    private Map<String, Producto> mapearCodigosPorProducto(List<String> idsProducto) {
        return servicioCodigo.obtenerCodigosConIdProductoEn(idsProducto)
                .stream()
                .collect(Collectors.toMap(Codigo::getIdCodigo, Codigo::getProducto));
    }

    private List<Lote> obtenerLotesDeCodigos(List<String> idsCodigos) {
        if (idsCodigos == null || idsCodigos.isEmpty()) return List.of();
        return servicioLote.obtenerLotesDeCodigos(idsCodigos);
    }

    private Map<String, Integer> agruparStockPorProducto(List<Lote> lotes, Map<String, Producto> codigosPorProducto) {
        return lotes.stream()
                .collect(Collectors.groupingBy(
                        l -> codigosPorProducto.get(l.getCodigo().getIdCodigo()).getIdProducto(),
                        Collectors.summingInt(l -> l.getStock() != null ? l.getStock().getCantidadActual() : 0)
                ));
    }

    private int calcularStockTotal(List<Codigo> codigos) {
        var idsCodigos = codigos.stream().map(Codigo::getIdCodigo).toList();
        var lotes = obtenerLotesDeCodigos(idsCodigos);
        return lotes.stream()
                .mapToInt(l -> l.getStock() != null ? l.getStock().getCantidadActual() : 0)
                .sum();
    }
}