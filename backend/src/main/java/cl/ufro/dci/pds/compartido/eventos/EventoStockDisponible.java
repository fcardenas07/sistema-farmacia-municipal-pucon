package cl.ufro.dci.pds.compartido.eventos;

public record EventoStockDisponible(
        Long idVenta,
        String idProducto,
        int cantidadSolicitada,
        boolean disponible
) {}