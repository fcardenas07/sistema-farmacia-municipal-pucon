package cl.ufro.dci.pds.inventario.app.dtos.anotaciones;

import cl.ufro.dci.pds.inventario.app.dtos.ProductoConStock;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StockValidoValidator implements ConstraintValidator<StockValido, ProductoConStock> {
    @Override
    public boolean isValid(ProductoConStock producto, ConstraintValidatorContext context) {
        if (producto.stockMaximo() != null && producto.stockMinimo() != null && producto.stockMaximo() < producto.stockMinimo()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El stock máximo debe ser mayor o igual al stock mínimo")
                    .addPropertyNode("stockValido")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}