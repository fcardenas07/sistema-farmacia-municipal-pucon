package cl.ufro.dci.pds.inventario.app.dtos;

import java.time.LocalDateTime;

public record EntradaIngresada(
        String idLote,
        String numeroLote,
        Integer cantidadIngresada,

        DatosProducto producto,
        DatosCodigo codigo,

        String idGuiaIngreso,

        LocalDateTime fechaElaboracion,
        LocalDateTime fechaVencimiento,
        String estado
) {

    public record DatosProducto(
            String idProducto,
            String nombre,
            String categoria
    ) {}

    public record DatosCodigo(
            String idCodigo,
            String codigoBarra,
            String tipoCodigo
    ) {}
}

