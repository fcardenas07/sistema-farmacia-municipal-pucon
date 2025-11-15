package cl.ufro.dci.pds.inventario.app.dtos;

public record ProductoModificado(
        String nombreComercial,
        String nombreGenerico,
        String presentacion,
        String dosificacion,
        String unidadMedida,
        Integer stockMinimo,
        Integer stockMaximo,
        String estado
) {}
