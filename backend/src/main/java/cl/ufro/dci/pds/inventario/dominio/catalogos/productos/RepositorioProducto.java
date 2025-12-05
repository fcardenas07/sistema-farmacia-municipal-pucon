package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioProducto extends JpaRepository<Producto, String> {

    @Query("""
    SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
    FROM Producto p
    WHERE p.nombreComercial = :nombreComercial
      AND p.nombreGenerico = :nombreGenerico
      AND ((p.presentacion IS NULL AND :presentacion IS NULL) OR p.presentacion = :presentacion)
      AND p.dosificacion = :dosificacion
      AND ((p.unidadMedida IS NULL AND :unidadMedida IS NULL) OR p.unidadMedida = :unidadMedida)
      AND ((p.fabricante IS NULL AND :idFabricante IS NULL) OR p.fabricante.id = :idFabricante)
""")
    boolean existsByClaveUnica(
            @Param("nombreComercial") String nombreComercial,
            @Param("nombreGenerico") String nombreGenerico,
            @Param("presentacion") String presentacion,
            @Param("dosificacion") Integer dosificacion,
            @Param("unidadMedida") String unidadMedida,
            @Param("idFabricante") String idFabricante
    );
}
