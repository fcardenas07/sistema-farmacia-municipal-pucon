package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.facturacion.boletas;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioBoleta extends JpaRepository<Boleta, String> {
    Optional<Boleta> findByVenta(Venta venta);
}