package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record DetallesPago(
    @NotBlank(message = "El metodo de pago es obligatorio")
    String metodoPago,
    @PositiveOrZero(message = "El monto es obligatorio")
    Integer monto,
    String referenciaTransaccion
) {

}
