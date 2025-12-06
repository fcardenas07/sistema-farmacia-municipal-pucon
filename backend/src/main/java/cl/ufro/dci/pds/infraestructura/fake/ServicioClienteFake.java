package cl.ufro.dci.pds.infraestructura.fake;

import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.Cliente;
import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.ServicioCliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class ServicioClienteFake implements ServicioCliente {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Cliente buscarPorRut(String rutCliente) {
        return em.getReference(Cliente.class, rutCliente);
    }
}
