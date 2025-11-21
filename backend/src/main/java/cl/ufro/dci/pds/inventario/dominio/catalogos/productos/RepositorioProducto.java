package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioProducto extends JpaRepository<Producto, String> {

    @Query("""
                SELECT p FROM Producto p
                WHERE (:nombreComercial IS NULL OR LOWER(p.nombreComercial) LIKE LOWER(CONCAT('%', :nombreComercial, '%')))
                  AND (:nombreGenerico IS NULL OR LOWER(p.nombreGenerico) LIKE LOWER(CONCAT('%', :nombreGenerico, '%')))
                  AND (:activo IS NULL OR p.activo = :activo)
                  AND (:categoria IS NULL OR p.categoria = :categoria)
            """)
    Page<Producto> buscarPorCampos(
            @Param("nombreComercial") String nombreComercial,
            @Param("nombreGenerico") String nombreGenerico,
            @Param("activo") Boolean activo,
            @Param("categoria") CategoriaProducto categoria,
            Pageable pageable
    );

    @Query("""
       SELECT p FROM Producto p
       WHERE (:nombreComercial IS NULL OR LOWER(p.nombreComercial) LIKE LOWER(CONCAT('%', :nombreComercial, '%')))
         AND (:nombreGenerico IS NULL OR LOWER(p.nombreGenerico) LIKE LOWER(CONCAT('%', :nombreGenerico, '%')))
         AND (:activo IS NULL OR p.activo = :activo)
         AND (:categoria IS NULL OR p.categoria = :categoria)
       """)
    List<Producto> buscarPorCampos(
            @Param("nombreComercial") String nombreComercial,
            @Param("nombreGenerico") String nombreGenerico,
            @Param("activo") Boolean activo,
            @Param("categoria") CategoriaProducto categoria
    );

    @Query("""
                SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
                FROM Producto p
                WHERE p.nombreComercial = :nombreComercial
                  AND p.nombreGenerico = :nombreGenerico
                  AND ((p.presentacion IS NULL AND :presentacion IS NULL) OR p.presentacion = :presentacion)
                  AND p.dosificacion = :dosificacion
                  AND ((p.unidadMedida IS NULL AND :unidadMedida IS NULL) OR p.unidadMedida = :unidadMedida)
                  AND p.idFabricante = :idFabricante
            """)
    boolean existsByClaveUnica(
            @Param("nombreComercial") String nombreComercial,
            @Param("nombreGenerico") String nombreGenerico,
            @Param("presentacion") String presentacion,
            @Param("dosificacion") int dosificacion,
            @Param("unidadMedida") String unidadMedida,
            @Param("idFabricante") String idFabricante
    );
}
