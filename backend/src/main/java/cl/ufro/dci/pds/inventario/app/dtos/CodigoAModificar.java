package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CodigoAModificar(
        @NotBlank(message = "El id del código no puede estar vacío")
        String idCodigo,

        @Size(max = 100, message = "El código de barra no puede tener más de 100 caracteres")
        String codigoBarra,

        @Size(max = 50, message = "El tipo de código no puede tener más de 50 caracteres")
        String tipoCodigo,

        Boolean activo
) implements CodigoConId{
    public void aplicarCambios(Codigo codigo) {
        if (codigoBarra != null) codigo.setCodigoBarra(codigoBarra);
        if (tipoCodigo != null) codigo.setTipoCodigo(tipoCodigo);
        if (activo != null) codigo.setActivo(activo);
    }
}
