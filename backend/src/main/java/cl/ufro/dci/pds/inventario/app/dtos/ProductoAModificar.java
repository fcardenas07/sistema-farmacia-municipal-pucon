package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.app.dtos.anotaciones.CodigosUnicos;
import cl.ufro.dci.pds.inventario.app.dtos.anotaciones.StockValido;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@StockValido(message = "El stock máximo debe ser mayor o igual al stock mínimo")
@CodigosUnicos(message =  "No se pueden repetir los IDs de los códigos")
public record ProductoAModificar(
        @Size(max = 200, message = "El nombre comercial no puede tener más de 200 caracteres")
        String nombreComercial,

        @Size(max = 200, message = "El nombre genérico no puede tener más de 200 caracteres")
        String nombreGenerico,

        @Size(max = 500, message = "La presentación no puede tener más de 500 caracteres")
        String presentacion,

        @Size(max = 100, message = "La dosificación no puede tener más de 100 caracteres")
        String dosificacion,

        @Size(max = 50, message = "La unidad de medida no puede tener más de 50 caracteres")
        String unidadMedida,

        @Min(value = 0, message = "El stock mínimo no puede ser negativo")
        @Max(value = 100_000_000, message = "El stock mínimo no puede superar 100.000.000")
        Integer stockMinimo,

        @Min(value = 0, message = "El stock máximo no puede ser negativo")
        @Max(value = 100_000_000, message = "El stock máximo no puede superar 100.000.000")
        Integer stockMaximo,

        @NotNull(message = "El estado activo no puede ser nulo")
        Boolean activo,

        CategoriaProducto categoria,

        @Valid
        List<CodigoAModificar> codigos
) implements ProductoConStock, ProductoConCodigos {
    public void aplicarCambios(Producto p) {
        if (nombreComercial != null) p.setNombreComercial(nombreComercial);
        if (nombreGenerico != null) p.setNombreGenerico(nombreGenerico);
        if (presentacion != null) p.setPresentacion(presentacion);
        if (dosificacion != null) p.setDosificacion(dosificacion);
        if (unidadMedida != null) p.setUnidadMedida(unidadMedida);
        if (stockMinimo != null) p.setStockMinimo(stockMinimo);
        if (stockMaximo != null) p.setStockMaximo(stockMaximo);
        if (activo != null) p.setActivo(activo);
        if (categoria != null) p.setCategoria(categoria);
    }
}
