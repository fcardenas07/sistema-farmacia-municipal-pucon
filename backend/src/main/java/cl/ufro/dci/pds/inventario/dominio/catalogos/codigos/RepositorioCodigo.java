package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioCodigo extends JpaRepository<Codigo, String> {

    boolean existsByIdCodigoAndProducto_IdProducto(String idCodigo, String idProducto);
    Optional<Codigo> findByIdCodigoAndProducto_IdProducto(String idCodigo, String idProducto);
    List<Codigo> findAllByProducto_IdProducto(String idProducto);
    List<Codigo> findAllByProducto_IdProductoIn(List<String> idsProductos);
}