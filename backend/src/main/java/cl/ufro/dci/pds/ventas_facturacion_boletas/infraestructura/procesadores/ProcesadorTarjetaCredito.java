package cl.ufro.dci.pds.ventas_facturacion_boletas.infraestructura.procesadores;

import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetallesPago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.infraestructura.ResultadoPago;

public interface ProcesadorTarjetaCredito {
    ResultadoPago procesar(DetallesPago detallesPago);
}
