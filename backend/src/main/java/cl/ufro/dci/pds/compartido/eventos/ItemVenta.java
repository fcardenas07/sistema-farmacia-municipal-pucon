package cl.ufro.dci.pds.compartido.eventos;

public record ItemVenta(
        String idLote,
        int cantidad,
        int precioUnitario
) {}