package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;

public record CodigoBuscado(
        String idCodigo,
        String codigoBarra
) {
    public static CodigoBuscado desde(Codigo codigo) {
        return new CodigoBuscado(
                codigo.getIdCodigo(),
                codigo.getCodigoBarra()
        );
    }
}
