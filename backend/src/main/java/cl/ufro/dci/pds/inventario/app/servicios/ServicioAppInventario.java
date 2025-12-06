package cl.ufro.dci.pds.inventario.app.servicios;

import cl.ufro.dci.pds.compartido.eventos.*;
import cl.ufro.dci.pds.infraestructura.BusEventosVentas;
import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.mermas.ServicioMerma;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.TipoMovimiento;
import cl.ufro.dci.pds.inventario.infraestructura.TrazabilidadLoteMapper;
import cl.ufro.dci.pds.inventario.infraestructura.RepositorioTrazabilidad;
import cl.ufro.dci.pds.inventario.app.mappers.EntradaInventarioMapper;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.ServicioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ServicioProducto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.ServicioMovimiento;
import jakarta.transaction.Transactional;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioAppInventario {

    private final ServicioLote servicioLote;
    private final ServicioCodigo servicioCodigo;
    private final ServicioProducto servicioProducto;
    private final ServicioMovimiento servicioMovimiento;
    private final ServicioMerma servicioMerma;
    private final EntradaInventarioMapper mapper;
    private final RepositorioTrazabilidad trazabilidadRepository;
    private final TrazabilidadLoteMapper trazabilidadLoteMapper;
    private final BusEventosVentas bus;

    public ServicioAppInventario(ServicioLote servicioLote,
                                 ServicioCodigo servicioCodigo,
                                 ServicioProducto servicioProducto,
                                 ServicioMovimiento servicioMovimiento,
                                 ServicioMerma servicioMerma,
                                 EntradaInventarioMapper mapper,
                                 RepositorioTrazabilidad trazabilidadRepository,
                                 TrazabilidadLoteMapper trazabilidadLoteMapper,
                                 BusEventosVentas bus) {
        this.servicioLote = servicioLote;
        this.servicioCodigo = servicioCodigo;
        this.servicioProducto = servicioProducto;
        this.servicioMovimiento = servicioMovimiento;
        this.servicioMerma = servicioMerma;
        this.mapper = mapper;
        this.trazabilidadRepository = trazabilidadRepository;
        this.trazabilidadLoteMapper = trazabilidadLoteMapper;
        this.bus = bus;
    }

    public void emitirStockDisponible(EventoStockDisponible evento) {
        bus.emitirStockDisponible(evento);
    }

    public void emitirInventarioActualizado(EventoInventarioActualizado evento) {
        bus.emitirInventarioActualizado(evento);
    }

    @EventListener
    public void manejarVentaIniciada(EventoVentaIniciada evento) {
        reservar(evento);

    }

    @EventListener
    public void manejarPagoAprobado(EventoPagoAprobado evento) {
        // procesar pago aprobado (luego se implementa)
    }

    @Transactional
    public EntradaIngresada crearLote(EntradaInventario dto) {
        var producto = servicioProducto.obtenerPorId(dto.codigo().idProducto());
        var codigo = servicioCodigo.obtenerOCrear(producto, dto.codigo());
        var lote = servicioLote.crear(dto, codigo);
        lote.setStockInicial(dto.cantidad());
        lote.setStockActual(dto.cantidad());
        servicioMovimiento.registarMovimientoPorEntradaInventario(lote, dto.cantidad(), producto.getNombreComercial());
        return mapper.toEntradaIngresada(lote, producto, codigo, null);
    }

    @Transactional
    public Page<TrazabilidadIngreso> obtenerIngresos(Pageable pageable) {
        var page = trazabilidadRepository.getIngresosOrdenados(pageable);
        return page.map(trazabilidadLoteMapper::toDto);
    }

    @Transactional
    public MovimientoBuscado obtenerMovimiento(String id) {
        var movimiento = servicioMovimiento.obtenerPorId(id);
        return trazabilidadLoteMapper.toDto(movimiento);
    }

    @Transactional
    public Page<MovimientoBuscado> obtenerMovimientosPor(TipoMovimiento tipoMovimiento, int page, int size) {
        var pageable = PageRequest.of(page, size);

        return servicioMovimiento.obtenerPorTipoMovimiento(tipoMovimiento, pageable)
                .map(trazabilidadLoteMapper::toDto);
    }

    @Transactional
    public List<LoteSimple> obtenerLotesPor(String filtro) {
        return servicioLote.obtenerPorNumeroLote(filtro).stream()
                .map(LoteSimple::desde)
                .toList();
    }

    @Transactional
    public String ingresarMerma(IngresoMerma dto) {
        var lote = servicioLote.obtenerPorId(dto.idLote());
        var cantidadDescontada = servicioLote.descontar(lote, dto.cantidad());

        var merma = dto.aEntidad(lote);
        merma.setCantidad(cantidadDescontada);

        merma = servicioMerma.guardar(merma);
        var movimiento = servicioMovimiento.registrarMovimientoPorMerma(lote, cantidadDescontada, merma.getDetalle());
        return movimiento.getIdMovimiento();
    }

    public void reservar(EventoVentaIniciada eventoVentaIniciada) {
        var itemsVentas = eventoVentaIniciada.items();
        var idVenta = eventoVentaIniciada.idVenta();
        var lotes = servicioLote.reservarLotes(itemsVentas);
        emitirStockDisponible(new EventoStockDisponible(idVenta, lotes, itemsVentas));
    }

}
