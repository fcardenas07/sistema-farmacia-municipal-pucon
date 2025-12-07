package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.facturacion.boletas;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ServicioBoleta {
    private final RepositorioBoleta repositorioBoleta;

    public ServicioBoleta(RepositorioBoleta repositorioBoleta) {
        this.repositorioBoleta = repositorioBoleta;
    }

    public Boleta generar(Venta venta) {
        var existente = repositorioBoleta.findByVenta(venta);
        if (existente.isPresent()) {
            return existente.get();
        }

        var boleta = new Boleta();
        boleta.setFechaEmision(LocalDateTime.now());
        boleta.setMontoTotal(venta.getTotal());
        boleta.setVenta(venta);

        return repositorioBoleta.save(boleta);
    }

    public Boleta obtenerPorId(String idBoleta) {
        return repositorioBoleta.findById(idBoleta)
                .orElseThrow(() -> new BoletaNoEncontradaException(idBoleta));
    }
}
