package cl.ufro.dci.pds.ventas_facturacion_boletas.infraestructura.procesadores;

import org.springframework.stereotype.Component;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetallesPago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.infraestructura.ResultadoPago;

@Component("procesadorTarjetaMock")
public class ProcesadorTarjetaCreditoMock implements ProcesadorTarjetaCredito {

    @Override
    public ResultadoPago procesar(DetallesPago detalles) {

        boolean aprobado = Math.random() > 0.5;

        return new ResultadoPago(
            aprobado,
            aprobado ? "Pago mock aprobado" : "Pago mock rechazado",
            "AUTH-MOCK-" + System.nanoTime()
        );
    }
}