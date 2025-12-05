package cl.ufro.dci.pds.infraestructura;

import cl.ufro.dci.pds.infraestructura.email.EmailContentBuilder;
import cl.ufro.dci.pds.infraestructura.email.EmailService;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    private final ServicioLote servicioLote;
    private final EmailService emailService;
    private final EmailContentBuilder emailBuilder;

    public ScheduledTasks(ServicioLote servicioLote,
                          EmailService emailService,
                          EmailContentBuilder emailBuilder) {
        this.servicioLote = servicioLote;
        this.emailService = emailService;
        this.emailBuilder = emailBuilder;
    }

    //cada 1 minuto(testing)
    //@Scheduled(cron = "0 * * * * *")
    //Cada 1 mes
    @Scheduled(cron = "0 0 8 1 * *")
    public void verificarVencimiento() {

        LocalDate hoy = LocalDate.now();
        LocalDate limite = hoy.plusMonths(1);

        var porVencer = servicioLote.obtener().stream()
                .filter(l -> l.getFechaVencimiento().isAfter(hoy))
                .filter(l -> l.getFechaVencimiento().isBefore(limite))
                .peek(l -> l.setEstado("POR_VENCER"))
                .toList();

        if (porVencer.isEmpty()) return;

        servicioLote.guardarTodos(porVencer);

        List<Lote> porVencerUnicos = porVencer.stream()
                .collect(Collectors.groupingBy(
                        Lote::getNumeroLote           // agrupamos por número de lote
                ))
                .entrySet().stream()
                .map(entry -> {

                    // lote representativo (cualquiera del grupo)
                    Lote representativo = entry.getValue().get(0);

                    // sumar cantidades
                    int cantidadTotal = entry.getValue().stream()
                            .mapToInt(l -> l.getStock().getCantidadActual())
                            .sum();

                    // asignar suma al lote representativo
                    representativo.getStock().setCantidadActual(cantidadTotal);

                    return representativo;
                })
                .toList();

        String html = emailBuilder.construirTablaLotesPorVencer(porVencerUnicos);

        emailService.enviar(
                "thomaswkm6@gmail.com",
                "Lotes por vencer en los próximos 30 días",
                html
        );
    }
}
