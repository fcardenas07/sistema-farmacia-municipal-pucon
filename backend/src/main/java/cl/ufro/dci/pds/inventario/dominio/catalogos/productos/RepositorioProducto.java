package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioProducto extends JpaRepository<Producto, String> {

    @Query("""
        SELECT p FROM Producto p
        WHERE (:idProducto IS NULL OR p.idProducto = :idProducto)
          AND (:nombreComercial IS NULL OR LOWER(p.nombreComercial) LIKE LOWER(CONCAT('%', :nombreComercial, '%')))
          AND (:nombreGenerico IS NULL OR LOWER(p.nombreGenerico) LIKE LOWER(CONCAT('%', :nombreGenerico, '%')))
          AND (:activo IS NULL OR p.activo = :activo)
    """)
    List<Producto> buscarPorCampos(
            @Param("idProducto") String idProducto,
            @Param("nombreComercial") String nombreComercial,
            @Param("nombreGenerico") String nombreGenerico,
            @Param("activo") Boolean activo
    );
}
