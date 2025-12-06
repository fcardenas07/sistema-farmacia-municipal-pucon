package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

import java.util.List;

public record VentaCreada(
        String idVenta,
        String estadoVenta,
        String fechaVenta,

        // Cliente
        String rutCliente,
        String nombreCliente,

        // Vendedor
        String idVendedor,
        String nombreVendedor,

        Integer total,

        // Detalles
        List<DetalleVentaCreado> detalles
) {}