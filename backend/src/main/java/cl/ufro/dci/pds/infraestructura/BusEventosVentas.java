package cl.ufro.dci.pds.infraestructura;

import cl.ufro.dci.pds.compartido.eventos.EventoInventarioActualizado;
import cl.ufro.dci.pds.compartido.eventos.EventoResultadoPago;
import cl.ufro.dci.pds.compartido.eventos.EventoStockDisponible;
import cl.ufro.dci.pds.compartido.eventos.EventoVentaIniciada;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component

public class BusEventosVentas {

    private final ApplicationEventPublisher publisher;

    public BusEventosVentas(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void emitirVentaIniciada(EventoVentaIniciada evento) {
        publisher.publishEvent(evento);
    }

    public void emitirStockDisponible(EventoStockDisponible evento) {
        publisher.publishEvent(evento);
    }

    public void emitirResultadoPago(EventoResultadoPago evento) {
        publisher.publishEvent(evento);
    }

    public void emitirInventarioActualizado(EventoInventarioActualizado evento) {
        publisher.publishEvent(evento);
    }
}
