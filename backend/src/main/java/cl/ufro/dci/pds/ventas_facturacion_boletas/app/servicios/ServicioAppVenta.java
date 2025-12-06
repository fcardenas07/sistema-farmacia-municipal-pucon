package cl.ufro.dci.pds.ventas_facturacion_boletas.app.servicios;

import cl.ufro.dci.pds.compartido.eventos.EventoInventarioActualizado;
import cl.ufro.dci.pds.compartido.eventos.EventoPagoAprobado;
import cl.ufro.dci.pds.compartido.eventos.EventoStockDisponible;
import cl.ufro.dci.pds.compartido.eventos.EventoVentaIniciada;
import cl.ufro.dci.pds.infraestructura.BusEventosVentas;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.*;
import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.ServicioCliente;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.ServicioUsuario;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetalleVentaACrear;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.VentaACrear;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.VentaCreada;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ServicioAppVenta {

    private final ServicioVenta servicioVenta;
    private final ServicioUsuario servicioUsuario;
    private final ServicioCliente servicioCliente;
    private final ServicioLote servicioLote;
    private final VentasMapper ventasMapper;
    private final BusEventosVentas bus;


    public ServicioAppVenta(ServicioVenta servicioVenta,
                            ServicioUsuario servicioUsuario,
                            ServicioCliente servicioCliente,
                            ServicioLote servicioLote,
                            VentasMapper ventasMapper,
                            BusEventosVentas bus) {
        this.servicioVenta = servicioVenta;
        this.servicioUsuario = servicioUsuario;
        this.servicioCliente = servicioCliente;
        this.servicioLote = servicioLote;
        this.ventasMapper = ventasMapper;
        this.bus = bus;
    }

    public void emitirVentaIniciada(EventoVentaIniciada evento) {
        bus.emitirVentaIniciada(evento);
    }

    public void emitirPagoAprobado(EventoPagoAprobado evento) {
        bus.emitirPagoAprobado(evento);
    }

    @EventListener
    public void manejarStockDisponible(EventoStockDisponible evento) {
        guardarVenta(evento);

    }

    @EventListener
    public void manejarInventarioActualizado(EventoInventarioActualizado evento) {
        // manejar inventario actualizado (luego se añade)
    }

    @Transactional
    public VentaCreada crear(@Valid VentaACrear dto) {
        var usuario = servicioUsuario.buscarPorId(dto.idVendedor());
        var cliente = servicioCliente.buscarPorRut(dto.rutCliente());

        var venta = servicioVenta.crear(
                usuario,
                cliente,
                dto.fechaVenta(),
                dto.detalleVenta()
        );

        var itemsVentas = dto.detalleVenta()
                        .stream()
                        .map(DetalleVentaACrear::toItemVenta)
                        .toList();

        emitirVentaIniciada(new EventoVentaIniciada(venta.getIdVenta(), itemsVentas));
        return ventasMapper.toDto(venta);
    }

    public VentaCreada guardarVenta(EventoStockDisponible evento){
        var venta = servicioVenta.buscarPorId(evento.idVenta());
        var lotes = evento.lotes();
        var itemsVenta = evento.items();

        var lotesPorId = lotes.stream()
                .collect(Collectors.toMap(Lote::getIdLote, Function.identity()));

        itemsVenta.stream()
                .map(item -> {

                    var lote = lotesPorId.get(item.idLote());
                    if (lote == null) {
                        throw new IllegalStateException("Lote no encontrado para item: " + item.idLote());
                    }

                    return new DetalleVenta(
                            venta,
                            lote,
                            item.cantidad(),
                            item.precioUnitario()
                    );
                })
                .forEach(venta.getDetallesVenta()::add);

        venta.recalcularTotal();

        venta.setEstadoVenta(EstadoVenta.PENDIENTE_PAGO);

        var ventaGuardada = servicioVenta.guardar(venta);

        return ventasMapper.toDto(ventaGuardada);

    }
}

