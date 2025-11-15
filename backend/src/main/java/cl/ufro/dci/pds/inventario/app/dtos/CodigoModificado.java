package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;

public record CodigoModificado(String idCodigo,
                               String codigosDeBarra
) {
    public static CodigoModificado desde(Codigo codigo) {
        return new CodigoModificado(
                codigo.getIdCodigo(),
                codigo.getCodigoBarra()
        );
    }
}