package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.app.dtos.anotaciones.StockValido;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import jakarta.validation.constraints.*;
import java.util.List;

@StockValido(message = "El stock máximo debe ser mayor o igual al stock mínimo")
public record ProductoACrear(
        @NotBlank(message = "El idProducto no puede ser vacío")
        String idProducto,

        @NotBlank(message = "El nombre comercial no puede ser vacío")
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
        int stockMinimo,

        @Min(value = 0, message = "El stock máximo no puede ser negativo")
        int stockMaximo,

        boolean activo,

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
        p.setActivo(activo);
        return p;
    }
}
