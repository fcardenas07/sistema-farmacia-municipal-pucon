package cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios;

public class UsuarioDuplicadoException extends RuntimeException {
    public UsuarioDuplicadoException(String mensaje) {
        super(mensaje);
    }
}