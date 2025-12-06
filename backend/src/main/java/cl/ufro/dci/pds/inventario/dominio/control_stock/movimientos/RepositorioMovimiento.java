package cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMovimiento extends JpaRepository<Movimiento, String> {
    Page<Movimiento> findByTipoMovimiento(TipoMovimiento tipoMovimiento, Pageable pageable);
}