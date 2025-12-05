package cl.ufro.dci.pds.compartido.eventos;

public record ItemInventarioActualizado(
        String idProducto,
        int stockAnterior,
        int stockActual
) {
}
