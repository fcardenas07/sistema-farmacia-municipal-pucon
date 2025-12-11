package cl.ufro.dci.pds.usuarios_permisos.app.dtos;


import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Rol;

public record AuthResponse(
        String token,
        String idUsuario,
        String username,
        String nombreCompleto,
        String email,
        Rol rol
) {
}
