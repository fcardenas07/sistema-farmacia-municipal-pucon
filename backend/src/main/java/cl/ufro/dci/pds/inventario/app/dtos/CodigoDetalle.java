package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;

public record CodigoDetalle(
        String idCodigo,
        String codigoBarra
) {
    public static CodigoDetalle desde(Codigo codigo) {
        return new CodigoDetalle(
                codigo.getIdCodigo(),
                codigo.getCodigoBarra()
        );
    }
}
