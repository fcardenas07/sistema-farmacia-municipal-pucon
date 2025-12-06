package cl.ufro.dci.pds.compartido.eventos;

public record ItemVenta(
        String idProducto,
        String idLote,
        int cantidad
) {}