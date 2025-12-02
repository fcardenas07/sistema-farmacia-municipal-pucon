package cl.ufro.dci.pds.inventario.app.dtos;

import java.time.LocalDate;

public record TrazabilidadLote(
        String idCodigo,
        String nombreComercial,
        String codigoLote,
        String fechaElaboracion,
        String fechaVencimiento,
        Integer cantidad,
        String estado,
        String ultimoMovimiento,
        String tipoMovimiento
) {
}
