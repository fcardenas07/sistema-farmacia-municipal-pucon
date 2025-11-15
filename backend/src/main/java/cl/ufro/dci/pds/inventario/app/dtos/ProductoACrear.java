package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;

import java.util.List;

public record ProductoACrear(
        String idProducto,
        String nombreComercial,
        String nombreGenerico,
        String presentacion,
        String dosificacion,
        String unidadMedida,
        int stockMinimo,
        int stockMaximo,
        String estado,
        List<CodigoACrear> codigos
) {
    public Producto aEntidad() {
        Producto p = new Producto();
        p.setIdProducto(idProducto);
        p.setNombreComercial(nombreComercial);
        p.setNombreGenerico(nombreGenerico);
        p.setPresentacion(presentacion);
        p.setDosificacion(dosificacion);
        p.setUnidadMedida(unidadMedida);
        p.setStockMinimo(stockMinimo);
        p.setStockMaximo(stockMaximo);
        p.setEstado(estado);
        return p;
    }
}