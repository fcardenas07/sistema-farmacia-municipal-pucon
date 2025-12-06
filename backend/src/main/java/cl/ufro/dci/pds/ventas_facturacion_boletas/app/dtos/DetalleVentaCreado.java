package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

public record DetalleVentaCreado(
        String idLote,
        String numeroLote,
        Integer cantidad,
        Integer precioUnitario,
        Integer subtotal
) {
}