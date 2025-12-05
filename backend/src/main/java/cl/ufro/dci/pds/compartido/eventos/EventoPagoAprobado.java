package cl.ufro.dci.pds.compartido.eventos;

public record EventoPagoAprobado(
        Long idVenta,
        boolean aprobado
) { }
