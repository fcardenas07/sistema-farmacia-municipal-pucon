package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioProducto extends JpaRepository<Producto, String> {

}
