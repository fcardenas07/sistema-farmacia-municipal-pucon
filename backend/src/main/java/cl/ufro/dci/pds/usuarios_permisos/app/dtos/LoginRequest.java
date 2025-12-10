package cl.ufro.dci.pds.usuarios_permisos.app.dtos;

public record LoginRequest(
        String username,
        String password
) {
}
