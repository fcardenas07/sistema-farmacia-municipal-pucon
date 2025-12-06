package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

public record SolicitudReservaLote(
        String idLote,
        int cantidad
) {}