package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
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

    @Query("""
    SELECT l
    FROM Lote l
    WHERE l.fechaVencimiento > :hoy
      AND l.fechaVencimiento < :limite
    """)
    List<Lote> findPorVencerEntre(LocalDateTime hoy, LocalDateTime limite);

    @Query(value = """
    SELECT COALESCE(SUM(l.stock_actual - l.stock_reservado), 0) AS productosPorVencer
    FROM lote l
    WHERE (l.stock_actual - l.stock_reservado) > 0
      -- solo lotes no vencidos
      AND l.fecha_vencimiento >= CURRENT_DATE
      -- días hasta vencimiento dentro del límite de merma
      AND DATE_PART('day', l.fecha_vencimiento - CURRENT_DATE)
          <= COALESCE(l.limite_merma, 0)
    """,
            nativeQuery = true
    )
    int contarUnidadesPorVencer();
}