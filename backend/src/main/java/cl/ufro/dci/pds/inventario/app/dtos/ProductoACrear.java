package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.app.dtos.anotaciones.StockValido;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import jakarta.validation.constraints.*;

@StockValido
public record ProductoACrear(
        @NotBlank(message = "El nombre comercial no puede estar vacío")
        @Size(max = 200, message = "El nombre comercial no puede tener más de 200 caracteres")
        String nombreComercial,

        @NotBlank(message = "El nombre genérico no puede estar vacío")
        @Size(max = 200, message = "El nombre genérico no puede tener más de 200 caracteres")
        String nombreGenerico,

        @Size(max = 500, message = "La presentación no puede tener más de 500 caracteres")
        String presentacion,

        @Min(value = 1, message = "La dosificación debe ser mayor a 0")
        @Max(value = 10_000, message = "La dosificación no debe ser mayor a 10000")
        int dosificacion,

        @Size(max = 50, message = "La unidad de medida no puede tener más de 50 caracteres")
        String unidadMedida,

        @Min(value = 0, message = "El stock mínimo no puede ser negativo")
        @Max(value = 100_000_000, message = "El stock mínimo no puede superar 100.000.000")
        Integer stockMinimo,

        @Min(value = 0, message = "El stock máximo no puede ser negativo")
        @Max(value = 100_000_000, message = "El stock máximo no puede superar 100.000.000")
        Integer stockMaximo,

        @NotNull(message = "La categoría es obligatoria")
        CategoriaProducto categoria,

        @NotBlank(message = "El fabricante es obligatorio")
        String idFabricante

) implements ProductoConStock {
    public Producto aEntidad() {
        Producto p = new Producto();
        p.setNombreComercial(nombreComercial);
        p.setNombreGenerico(nombreGenerico);
        p.setPresentacion(presentacion);
        p.setDosificacion(dosificacion);
        p.setUnidadMedida(unidadMedida);
        p.setStockMinimo(stockMinimo);
        p.setStockMaximo(stockMaximo);
        p.setActivo(true);
        p.setCategoria(categoria);
        return p;
    }
}
