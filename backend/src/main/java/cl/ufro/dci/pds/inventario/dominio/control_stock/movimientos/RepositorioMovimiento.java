package cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMovimiento extends JpaRepository<Movimiento, String> {

}