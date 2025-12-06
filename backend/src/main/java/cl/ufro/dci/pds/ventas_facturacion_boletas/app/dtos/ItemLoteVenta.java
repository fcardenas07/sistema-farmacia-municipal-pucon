package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;

public record ItemLoteVenta(Lote lote, Integer cantidad, Integer precioUnitario) {
}
