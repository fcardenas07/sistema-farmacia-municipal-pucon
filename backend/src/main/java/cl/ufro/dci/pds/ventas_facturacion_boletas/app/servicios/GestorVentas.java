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

    public GestorVentas(BusEventosVentas bus) {
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
        // manejar stock disponible (luego se añade)
    }

    @EventListener
    public void manejarInventarioActualizado(EventoInventarioActualizado evento) {
        // manejar inventario actualizado (luego se añade)
    }
}