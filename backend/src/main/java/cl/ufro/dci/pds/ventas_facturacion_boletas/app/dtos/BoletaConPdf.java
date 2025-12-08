package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.facturacion.boletas.Boleta;

public record BoletaConPdf(Boleta boleta,
                           byte[] pdf) {

    public static BoletaConPdf desdeEntidad(Boleta boleta, byte[] pdf) {
        return new BoletaConPdf(boleta, pdf);
    }
}
