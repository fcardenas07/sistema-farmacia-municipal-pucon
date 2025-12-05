package cl.ufro.dci.pds.compartido.eventos;

import java.util.List;

public record EventoInventarioActualizado(
        Long idVenta,
        List<ItemInventarioActualizado> productos
) {}