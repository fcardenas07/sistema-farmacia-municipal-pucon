package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos;


import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetallesPago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.infraestructura.ResultadoPago;

public interface PagoEstrategia {
    ResultadoPago ejecutar(DetallesPago detalles);
}
