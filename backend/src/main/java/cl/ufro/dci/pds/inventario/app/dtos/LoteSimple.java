package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;

public record LoteSimple(
        String idLote,
        String numeroLote
) {
    public static LoteSimple desde(Lote lote) {
        return new LoteSimple(
                lote.getIdLote(),
                lote.getNumeroLote()
        );
    }
}