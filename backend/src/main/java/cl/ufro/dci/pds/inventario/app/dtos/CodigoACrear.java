package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CodigoACrear(
        @NotBlank(message = "El id del código no puede estar vacío")
        String idCodigo,

        @NotBlank(message = "El código de barra no puede estar vacío")
        @Size(max = 100, message = "El código de barra no puede tener más de 100 caracteres")
        String codigoBarra,

        @NotBlank(message = "El tipo de código no puede estar vacío")
        @Size(max = 50, message = "El tipo de código no puede tener más de 50 caracteres")
        String tipoCodigo,

        boolean activo

) implements CodigoConId{
    public Codigo aEntidad(Producto producto) {
        Codigo c = new Codigo();
        c.setProducto(producto);
        c.setIdCodigo(idCodigo);
        c.setCodigoBarra(codigoBarra);
        c.setTipoCodigo(tipoCodigo);
        c.setActivo(activo);
        return c;
    }
}
