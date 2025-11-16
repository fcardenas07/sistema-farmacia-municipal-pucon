package cl.ufro.dci.pds.inventario.app.dtos.anotaciones;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CodigosUnicosValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CodigosUnicos {
    String message() default "No puede haber códigos duplicados";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}