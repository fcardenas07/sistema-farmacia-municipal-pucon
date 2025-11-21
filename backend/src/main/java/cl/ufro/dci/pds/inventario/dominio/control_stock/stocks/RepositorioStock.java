package cl.ufro.dci.pds.inventario.dominio.control_stock.stocks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioStock extends JpaRepository<Stock, String> {
}
