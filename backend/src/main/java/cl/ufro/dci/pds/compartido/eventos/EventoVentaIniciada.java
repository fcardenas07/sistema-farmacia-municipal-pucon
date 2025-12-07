package cl.ufro.dci.pds.compartido.eventos;

import java.util.List;

public record EventoVentaIniciada(
        String idVenta,
        List<ItemVenta> items
) {}