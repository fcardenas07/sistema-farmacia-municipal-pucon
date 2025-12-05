package cl.ufro.dci.pds.infraestructura;

import cl.ufro.dci.pds.infraestructura.email.EmailContentBuilder;
import cl.ufro.dci.pds.infraestructura.email.EmailService;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
        var hoy = LocalDate.now();
        var limite = hoy.plusMonths(1);

        var porVencer = servicioLote.obtenerPorVencerEntre(hoy, limite);
        if (porVencer.isEmpty()) return;

        porVencer.forEach(l -> l.setEstado("POR_VENCER"));
        servicioLote.guardarTodos(porVencer);

        var porVencerUnicos = porVencer.stream()
                .collect(Collectors.groupingBy(Lote::getNumeroLote))
                .values().stream()
                .map(lotes -> {
                    var representativo = lotes.getFirst();
                    int cantidadTotal = lotes.stream()
                            .mapToInt(Lote::getStockActual)
                            .sum();
                    representativo.setStockActual(cantidadTotal);
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
