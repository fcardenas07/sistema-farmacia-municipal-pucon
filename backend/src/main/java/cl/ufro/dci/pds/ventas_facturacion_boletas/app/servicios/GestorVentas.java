package cl.ufro.dci.pds.ventas_facturacion_boletas.app.servicios;

import cl.ufro.dci.pds.compartido.eventos.EventoInventarioActualizado;
import cl.ufro.dci.pds.compartido.eventos.EventoPagoAprobado;
import cl.ufro.dci.pds.compartido.eventos.EventoStockDisponible;
import cl.ufro.dci.pds.compartido.eventos.EventoVentaIniciada;
import cl.ufro.dci.pds.infraestructura.BusEventosVentas;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GestorVentas {

    private final BusEventosVentas bus;
    private final ServicioAppVenta servicioAppVenta;

    public GestorVentas(BusEventosVentas bus, ServicioAppVenta servicioAppVenta) {
        this.bus = bus;
        this.servicioAppVenta = servicioAppVenta;
    }

    public void emitirVentaIniciada(EventoVentaIniciada evento) {
        bus.emitirVentaIniciada(evento);
    }

    public void emitirPagoAprobado(EventoPagoAprobado evento) {
        bus.emitirPagoAprobado(evento);
    }

    @EventListener
    public void manejarStockDisponible(EventoStockDisponible evento) {
        servicioAppVenta.guardarVenta(evento);

    }

    @EventListener
    public void manejarInventarioActualizado(EventoInventarioActualizado evento) {
        // manejar inventario actualizado (luego se añade)
    }
}