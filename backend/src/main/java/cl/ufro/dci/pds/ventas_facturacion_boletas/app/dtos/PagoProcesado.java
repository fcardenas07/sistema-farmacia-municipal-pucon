package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

import java.time.LocalDateTime;

public record PagoProcesado(
        String idPago,
        String idVenta,
        String idBoleta,
        Integer monto,
        String metodoPago,
        String estado,
        String referenciaTransaccion,
        String mensajeProcesamiento,
        String codigoAutorizacion,
        LocalDateTime fechaPago
) {}