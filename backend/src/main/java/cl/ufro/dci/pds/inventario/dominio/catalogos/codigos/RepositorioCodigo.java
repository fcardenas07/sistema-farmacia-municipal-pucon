package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface RepositorioCodigo extends JpaRepository<Codigo, String> {

    Optional<Codigo> findByIdCodigoAndProducto_IdProducto(String idCodigo, String idProducto);
    List<Codigo> findAllByProducto_IdProducto(String idProducto);
    Optional<Codigo> findByCodigoBarra(String codigoBarra);

}