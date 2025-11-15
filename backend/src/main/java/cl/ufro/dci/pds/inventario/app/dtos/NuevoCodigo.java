package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;

public record NuevoCodigo(
        String idCodigo,
        String codigoBarra,
        String tipoCodigo,
        boolean activo
) {
    public Codigo aEntidad() {
        Codigo c = new Codigo();
        c.setIdCodigo(idCodigo);
        c.setCodigoBarra(codigoBarra);
        c.setTipoCodigo(tipoCodigo);
        c.setActivo(activo);
        return c;
    }
}