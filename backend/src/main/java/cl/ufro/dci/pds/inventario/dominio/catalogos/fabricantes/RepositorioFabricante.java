package cl.ufro.dci.pds.inventario.dominio.catalogos.fabricantes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioFabricante extends JpaRepository<Fabricante, String> {
}
