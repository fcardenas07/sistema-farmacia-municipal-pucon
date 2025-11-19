package cl.ufro.dci.pds.inventario.app.dtos.anotaciones;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CodigosBarraUnicosValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CodigosBarraUnicos {
    String message() default "No puede haber códigos de barra duplicados";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}