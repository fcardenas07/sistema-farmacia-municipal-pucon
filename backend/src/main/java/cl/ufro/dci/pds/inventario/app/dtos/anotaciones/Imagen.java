package cl.ufro.dci.pds.inventario.app.dtos.anotaciones;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = ImagenValidator.class)
public @interface Imagen {
    String message() default "El archivo debe ser una imagen";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
