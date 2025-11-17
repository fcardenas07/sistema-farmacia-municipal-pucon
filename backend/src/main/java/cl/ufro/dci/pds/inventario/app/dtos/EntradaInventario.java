package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;

import java.time.LocalDate;

public record EntradaInventario(
        String idProducto,
        String idLote,
        String numeroLote,
        LocalDate fechaElaboracion,
        LocalDate fechaVencimiento,
        Integer cantidad,
        Integer precio
) {
}