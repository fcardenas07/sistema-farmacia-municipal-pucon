package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;

public record CodigoModificado(
        String idCodigo,
        String codigoBarra,
        String tipoCodigo,
        Boolean activo
) {
    public void aplicarCambios(Codigo codigo) {
        if (codigoBarra != null) codigo.setCodigoBarra(codigoBarra);
        if (tipoCodigo != null) codigo.setTipoCodigo(tipoCodigo);
        if (activo != null) codigo.setActivo(activo);
    }
}