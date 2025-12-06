package cl.ufro.dci.pds.inventario.app.servicios;

import cl.ufro.dci.pds.compartido.eventos.EventoInventarioActualizado;
import cl.ufro.dci.pds.compartido.eventos.EventoPagoAprobado;
import cl.ufro.dci.pds.compartido.eventos.EventoStockDisponible;
import cl.ufro.dci.pds.compartido.eventos.EventoVentaIniciada;
import cl.ufro.dci.pds.infraestructura.BusEventosVentas;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GestorInventario {

    private final BusEventosVentas bus;

    public GestorInventario(BusEventosVentas bus) {
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
        // procesar venta iniciada (luego se implementa)
    }

    @EventListener
    public void manejarPagoAprobado(EventoPagoAprobado evento) {
        // procesar pago aprobado (luego se implementa)
    }
}
