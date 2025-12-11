package cl.ufro.dci.pds.usuarios_permisos.infraestructura;

import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
