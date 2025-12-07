package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;

public record ItemLoteCantidad(
        Lote lote,
        Integer cantidad
) {
}
