package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

public record ItemBoleta(
        String nombreProducto,
        Integer cantidad,
        Integer precioUnitario,
        Integer subtotal) {
}
