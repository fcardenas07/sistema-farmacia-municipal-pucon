package cl.ufro.dci.pds.compartido.eventos;

import java.util.List;

public record EventoVentaIniciada(
        Long idVenta,
        List<ItemVenta> items
) {}