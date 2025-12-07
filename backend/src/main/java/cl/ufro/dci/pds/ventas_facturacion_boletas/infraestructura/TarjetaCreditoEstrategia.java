package cl.ufro.dci.pds.ventas_facturacion_boletas.infraestructura;

import org.springframework.stereotype.Service;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos.PagoEstrategia;
import cl.ufro.dci.pds.ventas_facturacion_boletas.infraestructura.procesadores.ProcesadorTarjetaCredito;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetallesPago;

@Service("tarjetaCredito")
public class TarjetaCreditoEstrategia implements PagoEstrategia {

    private final ProcesadorTarjetaCredito procesadorTarjetaCredito;

    public TarjetaCreditoEstrategia(ProcesadorTarjetaCredito procesadorTarjetaCredito) {
        this.procesadorTarjetaCredito = procesadorTarjetaCredito;
    }

    @Override
    public ResultadoPago ejecutar(DetallesPago detalles) {
        return procesadorTarjetaCredito.procesar(detalles);
    }
}
