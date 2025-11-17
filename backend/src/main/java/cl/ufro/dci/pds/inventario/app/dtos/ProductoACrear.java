package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.app.dtos.anotaciones.CodigosUnicos;
import cl.ufro.dci.pds.inventario.app.dtos.anotaciones.StockValido;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@StockValido(message = "El stock máximo debe ser mayor o igual al stock mínimo")
@CodigosUnicos(message =  "No se pueden repetir los IDs de los códigos")
public record ProductoACrear(
        @NotBlank(message = "El id del producto no puede estar vacío")
        String idProducto,

        @NotBlank(message = "El nombre comercial no puede estar vacío")
        @Size(max = 200, message = "El nombre comercial no puede tener más de 200 caracteres")
        String nombreComercial,

        @NotBlank(message = "El nombre genérico no puede estar vacío")
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

        boolean activo,

        @Valid
        @NotEmpty(message = "El producto debe tener al menos un código")
        List<CodigoACrear> codigos

) implements ProductoConStock, ProductoConCodigos {
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
        p.setActivo(activo);
        return p;
    }
}
