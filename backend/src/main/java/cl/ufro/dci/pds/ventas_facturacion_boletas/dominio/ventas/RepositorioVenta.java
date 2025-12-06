package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioVenta extends JpaRepository<Venta, String> {
}
