package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioCodigo extends JpaRepository<Codigo, String> {

    boolean existsByIdAndProductoId(String idCodigo, String idProducto);
    Optional<Codigo> findByIdAndProductoId(String idCodigo, String idProducto);
}