package cl.ufro.dci.pds.inventario.app.dtos;

public record MovimientoBuscado(
    String idMovimiento,
    String tipoMovimiento,
    Integer cantidad,
    String fechaMovimiento,
    String detalle
) {
}
