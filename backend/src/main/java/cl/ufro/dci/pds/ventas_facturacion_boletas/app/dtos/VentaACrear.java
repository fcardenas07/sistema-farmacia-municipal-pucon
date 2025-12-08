package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record VentaACrear(
        @NotBlank(message = "El rut del cliente es obligatorio")
        String rutCliente,
        @NotBlank(message = "La id del venedor no puede ser vacia")
        String idVendedor,
        @Valid
        List<DetalleVentaACrear> detalleVenta
) {
}