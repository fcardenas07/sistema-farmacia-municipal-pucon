package cl.ufro.dci.pds.testmother.inventario.objectmother;

import cl.ufro.dci.pds.inventario.app.dtos.EntradaIngresada;

import java.time.LocalDateTime;

public final class EntradaIngresadaMother {

    private EntradaIngresadaMother() {}

    public static EntradaIngresada crearValida() {

        var fechaElaboracion = LocalDateTime.now().minusDays(10);
        var fechaVencimiento = LocalDateTime.now().plusMonths(12);

        return new EntradaIngresada(
                "L-12345-ID",                      // idLote
                "L-12345",                         // numeroLote
                100,                               // cantidadIngresada
                new EntradaIngresada.DatosProducto(
                        "P001",
                        "Paracetamol 500mg",
                        "ANALGÉSICO"
                ),
                new EntradaIngresada.DatosCodigo(
                        "COD123-ID",
                        "7891234567890",
                        "EAN13"
                ),
                "G-2025-001",                       // idGuiaIngreso
                fechaElaboracion,
                fechaVencimiento,
                "ACTIVO"                            // estado
        );
    }
}
