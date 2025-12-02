package cl.ufro.dci.pds.inventario.app.dtos;

public record TrazabilidadIngreso(
        String idCodigo,
        String nombreComercial,
        String numeroLote,
        String fechaElaboracion,
        String fechaVencimiento,
        Integer cantidad,
        String estado,
        String fechaMovimiento,
        String tipoMovimiento
) {
}
