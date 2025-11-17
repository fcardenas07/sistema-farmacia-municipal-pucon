package cl.ufro.dci.pds.inventario.app.dtos.anotaciones;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImagenValidator implements ConstraintValidator<Imagen, MultipartFile> {

    private static final long MAX_BYTES = 10 * 1024 * 1024;

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return construirError(context, "El archivo no puede estar vacío");
        }

        if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            return construirError(context, "El archivo debe tener un nombre válido");
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg"))) {
            return construirError(context, "El archivo debe ser una imagen PNG o JPG");
        }

        if (file.getSize() > MAX_BYTES) {
            return construirError(context, "El archivo no puede superar los 10 MB");
        }

        return true;
    }

    private boolean construirError(ConstraintValidatorContext context, String mensaje) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(mensaje)
                .addConstraintViolation();
        return false;
    }
}