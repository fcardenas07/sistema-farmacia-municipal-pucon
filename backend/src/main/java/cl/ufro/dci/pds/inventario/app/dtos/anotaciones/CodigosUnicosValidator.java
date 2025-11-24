package cl.ufro.dci.pds.inventario.app.dtos.anotaciones;

import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class CodigosUnicosValidator implements ConstraintValidator<CodigosUnicos, ProductoAModificar> {

    @Override
    public boolean isValid(ProductoAModificar producto, ConstraintValidatorContext context) {
        var codigos = producto.codigos();
        if (codigos == null || codigos.isEmpty()) {
            return true;
        }

        Set<String> vistos = new HashSet<>();
        for (var codigo : codigos) {
            if (codigo.idCodigo() == null) continue;
            if (!vistos.add(codigo.idCodigo())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("No se pueden repetir los IDs de los códigos")
                        .addPropertyNode("codigosUnicos")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
