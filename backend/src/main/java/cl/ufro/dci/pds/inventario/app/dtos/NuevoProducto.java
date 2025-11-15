package cl.ufro.dci.pds.inventario.app.dtos;

public record NuevoProducto(
        String idProducto,
        String nombreComercial,
        String nombreGenerico,
        String presentacion,
        String dosificacion,
        String unidadMedida,
        int stockMinimo,
        int stockMaximo,
        String estado
) {}
