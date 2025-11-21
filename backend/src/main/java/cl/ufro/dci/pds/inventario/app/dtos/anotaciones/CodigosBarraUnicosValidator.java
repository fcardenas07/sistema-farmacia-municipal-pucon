package cl.ufro.dci.pds.inventario.app.dtos.anotaciones;

import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;

public class CodigosBarraUnicosValidator implements ConstraintValidator<CodigosBarraUnicos, ProductoAModificar> {

    @Override
    public boolean isValid(ProductoAModificar producto, ConstraintValidatorContext context) {
        var codigos = producto.codigos();
        if (codigos == null || codigos.isEmpty()) return true;

        var vistos = new HashSet<>();
        for (var codigo : codigos) {
            if (!vistos.add(codigo.codigoBarra())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("No puede haber códigos de barra duplicados")
                        .addPropertyNode("codigosBarraUnicos")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}