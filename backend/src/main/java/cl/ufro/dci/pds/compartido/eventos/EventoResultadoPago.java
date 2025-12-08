package cl.ufro.dci.pds.compartido.eventos;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos.EstadoPago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;

public record EventoResultadoPago(
        Venta venta,
        EstadoPago estadoPago
) { }
