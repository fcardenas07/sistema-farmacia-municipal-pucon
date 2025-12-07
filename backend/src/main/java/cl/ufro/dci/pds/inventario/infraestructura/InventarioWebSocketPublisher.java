package cl.ufro.dci.pds.inventario.infraestructura;

import cl.ufro.dci.pds.compartido.eventos.EventoInventarioActualizado;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventarioWebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public InventarioWebSocketPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void onInventarioActualizado(EventoInventarioActualizado evento) {
        messagingTemplate.convertAndSend("/topic/inventario-actualizado", evento);
    }
}