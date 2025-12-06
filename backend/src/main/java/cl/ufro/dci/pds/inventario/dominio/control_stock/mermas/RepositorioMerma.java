package cl.ufro.dci.pds.inventario.dominio.control_stock.mermas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioMerma extends JpaRepository<Merma, String> {
}
