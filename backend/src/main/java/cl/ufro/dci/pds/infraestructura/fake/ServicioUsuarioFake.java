package cl.ufro.dci.pds.infraestructura.fake;

import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.ServicioUsuario;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarioFake implements ServicioUsuario {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Usuario buscarPorId(String idUsuario) {
        return em.getReference(Usuario.class, idUsuario);
    }
}

