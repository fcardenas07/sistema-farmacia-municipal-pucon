package cl.ufro.dci.pds.inventario.app.dtos.anotaciones;

import cl.ufro.dci.pds.inventario.app.dtos.ProductoACrear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StockValidoValidator implements ConstraintValidator<StockValido, ProductoACrear> {
    @Override
    public boolean isValid(ProductoACrear producto, ConstraintValidatorContext context) {
        return producto.stockMaximo() >= producto.stockMinimo();
    }
}