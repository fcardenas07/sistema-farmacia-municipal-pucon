package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;

public record CodigoCreado(
        String idCodigo,
        String codigosDeBarra
) {
    public static CodigoCreado desde(Codigo codigo) {
        return new CodigoCreado(
                codigo.getIdCodigo(),
                codigo.getCodigoBarra()
        );
    }
}