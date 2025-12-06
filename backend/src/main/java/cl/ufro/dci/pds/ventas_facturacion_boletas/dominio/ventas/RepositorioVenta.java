package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioVenta extends JpaRepository<Venta, String> {
}
