package cl.ufro.dci.pds.ventas_facturacion_boletas.infraestructura;

public record ResultadoPago(
        boolean aprobado,
        String mensaje,
        String codigoAutorizacion
) {}
