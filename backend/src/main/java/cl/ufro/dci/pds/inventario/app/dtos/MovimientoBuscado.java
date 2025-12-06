package cl.ufro.dci.pds.inventario.app.dtos;

import java.time.LocalDateTime;

public record MovimientoBuscado(
    String idMovimiento,
    String tipoMovimiento,
    Integer cantidad,
    LocalDateTime fechaMovimiento,
    String detalle
) {
}
