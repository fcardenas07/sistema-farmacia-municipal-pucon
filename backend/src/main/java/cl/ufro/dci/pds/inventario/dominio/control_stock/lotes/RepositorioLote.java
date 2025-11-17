package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioLote extends JpaRepository<Lote, String> {
    Optional<Lote> findByNumeroLote(String numeroLote);
}
