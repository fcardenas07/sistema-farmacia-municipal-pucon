package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

public record CodigoACrear(
        @NotBlank(message = "El código de barra no puede estar vacío")
        @Size(max = 100, message = "El código de barra no puede tener más de 100 caracteres")
        String codigoBarra,

        @NotBlank(message = "El tipo de código no puede estar vacío")
        @Size(max = 50, message = "El tipo de código no puede tener más de 50 caracteres")
        String tipoCodigo,

        boolean activo,

        @NotBlank(message = "La id del producto no puede estar vacía")
        String idProducto

) implements CodigoConCodigoBarra{
    public Codigo aEntidad(Producto producto) {
        Codigo c = new Codigo();
        c.setProducto(producto);
        c.setCodigoBarra(codigoBarra);
        c.setTipoCodigo(tipoCodigo);
        c.setActivo(activo);
        return c;
    }
}
