package cl.ufro.dci.pds.inventario.app.dtos.anotaciones;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StockValidoValidator.class)
public @interface StockValido {
    String message() default "El stock máximo debe ser mayor o igual al stock mínimo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
