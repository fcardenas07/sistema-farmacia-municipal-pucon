package cl.ufro.dci.pds.compartido.eventos;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;

import java.util.List;

public record EventoStockDisponible(
        String idVenta,
        List<Lote> lotes,
        List<ItemVenta> items
) {}