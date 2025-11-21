package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.app.dtos.anotaciones.CodigosBarraUnicos;
import cl.ufro.dci.pds.inventario.app.dtos.anotaciones.CodigosUnicos;
import cl.ufro.dci.pds.inventario.app.dtos.anotaciones.StockValido;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@StockValido
@CodigosUnicos
@CodigosBarraUnicos
public record ProductoAModificar(
        @Size(max = 200, message = "El nombre comercial no puede tener más de 200 caracteres")
        String nombreComercial,

        @Size(max = 200, message = "El nombre genérico no puede tener más de 200 caracteres")
        String nombreGenerico,

        @Size(max = 500, message = "La presentación no puede tener más de 500 caracteres")
        String presentacion,

        @Min(value = 1, message = "La dosificación debe ser mayor a 0")
        @Max(value = 10_000, message = "La dosificación no debe ser mayor a 10000")
        Integer dosificacion,

        @Size(max = 50, message = "La unidad de medida no puede tener más de 50 caracteres")
        String unidadMedida,

        @Min(value = 0, message = "El stock mínimo no puede ser negativo")
        @Max(value = 100_000_000, message = "El stock mínimo no puede superar 100.000.000")
        Integer stockMinimo,

        @Min(value = 0, message = "El stock máximo no puede ser negativo")
        @Max(value = 100_000_000, message = "El stock máximo no puede superar 100.000.000")
        Integer stockMaximo,

        CategoriaProducto categoria,

        @Valid
        List<CodigoAModificar> codigos
) implements ProductoConStock {
    public void aplicarCambios(Producto p) {
        if (nombreComercial != null) p.setNombreComercial(nombreComercial);
        if (nombreGenerico != null) p.setNombreGenerico(nombreGenerico);
        if (presentacion != null) p.setPresentacion(presentacion);
        if (dosificacion != null) p.setDosificacion(dosificacion);
        if (unidadMedida != null) p.setUnidadMedida(unidadMedida);
        if (stockMinimo != null) p.setStockMinimo(stockMinimo);
        if (stockMaximo != null) p.setStockMaximo(stockMaximo);
        if (categoria != null) p.setCategoria(categoria);
    }
}
