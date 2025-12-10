package cl.ufro.dci.pds.usuarios_permisos.app.dtos;

import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioACrearRequest(
        @NotBlank(message = "El username es requerido")
        @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
        String username,

        @NotBlank(message = "La contraseña es requerida")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        @NotBlank(message = "El nombre completo es requerido")
        @Size(max = 100, message = "El nombre completo no puede exceder los 100 caracteres")
        String nombreCompleto,

        @NotBlank(message = "El email es requerido")
        @Email(message = "El formato del email no es válido")
        @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
        String email,

        @NotNull(message = "El rol es requerido")
        Rol rol
) {
}