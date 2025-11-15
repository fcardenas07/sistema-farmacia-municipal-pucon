package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

import java.util.List;

public record ProductoAModificar(
        String nombreComercial,
        String nombreGenerico,
        String presentacion,
        String dosificacion,
        String unidadMedida,
        Integer stockMinimo,
        Integer stockMaximo,
        String estado,
        List<CodigoAModificar> codigos
) {
    public void aplicarCambios(Producto p) {
        if (nombreComercial != null) p.setNombreComercial(nombreComercial);
        if (nombreGenerico != null) p.setNombreGenerico(nombreGenerico);
        if (presentacion != null) p.setPresentacion(presentacion);
        if (dosificacion != null) p.setDosificacion(dosificacion);
        if (unidadMedida != null) p.setUnidadMedida(unidadMedida);
        if (stockMinimo != null) p.setStockMinimo(stockMinimo);
        if (stockMaximo != null) p.setStockMaximo(stockMaximo);
        if (estado != null) p.setEstado(estado);
    }
}
