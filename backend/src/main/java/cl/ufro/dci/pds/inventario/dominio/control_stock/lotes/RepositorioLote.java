package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface RepositorioLote extends JpaRepository<Lote, String> {
    Optional<Lote> findByNumeroLote(String numeroLote);
    List<Lote> findByNumeroLoteStartingWithIgnoreCase(String prefijo);

    @Query("""
            SELECT DISTINCT l
            FROM Lote l
            JOIN FETCH l.codigo c
            WHERE c.idCodigo IN :ids
            """)
    List<Lote> findByCodigo_IdCodigoIn(List<String> ids);
}