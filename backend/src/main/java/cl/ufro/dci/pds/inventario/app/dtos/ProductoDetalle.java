package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.infraestructura.ProyeccionProductoDetalle;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ProductoDetalle(
        String idProducto,
        String nombreComercial,
        String nombreGenerico,
        String presentacion,
        String nombreFabricante,
        int dosificacion,
        String unidadMedida,
        boolean activo,
        int stockTotal,
        int stockMinimo,
        int stockMaximo,
        String urlFoto,
        List<CodigoDetalle> codigos
) {

    @JsonProperty("estaDisponible")
    public boolean disponible() {
        return stockTotal > 0 && activo;
    }

    public static ProductoDetalle desdeProyeccion(ProyeccionProductoDetalle p, List<Codigo> codigos) {
        var codigosDto = codigos.stream()
                .map(CodigoDetalle::desde)
                .toList();

        return new ProductoDetalle(
                p.getIdProducto(),
                p.getNombreComercial(),
                p.getNombreGenerico(),
                p.getPresentacion(),
                p.getNombreFabricante(),
                p.getDosificacion() != null ? p.getDosificacion() : 0,
                p.getUnidadMedida(),
                Boolean.TRUE.equals(p.getActivo()),
                p.getStockTotal() != null ? p.getStockTotal() : 0,
                p.getStockMinimo(),
                p.getStockMaximo(),
                p.getUrlFoto(),
                codigosDto
        );
    }
}