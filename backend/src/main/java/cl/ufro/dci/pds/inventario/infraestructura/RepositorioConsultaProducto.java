package cl.ufro.dci.pds.inventario.infraestructura;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioConsultaProducto extends JpaRepository<Producto, String> {

    @Query(value = """
                SELECT
                    p.id_producto          AS idProducto,
                    p.nombre_comercial     AS nombreComercial,
                    p.nombre_generico      AS nombreGenerico,
                    f.nombre               AS nombreFabricante,
                    p.dosificacion         AS dosificacion,
                    p.unidad_medida        AS unidadMedida,
                    p.stock_minimo         AS stockMinimo,
                    p.stock_maximo         AS stockMaximo,
                    p.url_foto             AS urlFoto,
            
                    COALESCE(SUM(l.stock_actual), 0) AS stockTotal
            
                FROM producto p
                LEFT JOIN fabricante f ON p.id_fabricante = f.id_fabricante
                LEFT JOIN codigo c ON c.id_producto = p.id_producto
                LEFT JOIN lote l ON l.id_codigo = c.id_codigo
            
                WHERE (:nombreComercial IS NULL OR LOWER(p.nombre_comercial) LIKE LOWER(CONCAT('%', :nombreComercial, '%')))
                  AND (:nombreGenerico  IS NULL OR LOWER(p.nombre_generico)  LIKE LOWER(CONCAT('%', :nombreGenerico, '%')))
                  AND (:categoria       IS NULL OR p.categoria = :categoria)
                  AND p.activo = TRUE
            
                GROUP BY
                    p.id_producto, p.nombre_comercial, p.nombre_generico, f.nombre,
                    p.dosificacion, p.unidad_medida, p.stock_minimo, p.stock_maximo, p.url_foto
            """, nativeQuery = true)
    List<ProyeccionProductoFiltrado> buscarProductosConStock(@Param("nombreComercial") String nombreComercial, @Param("nombreGenerico") String nombreGenerico, @Param("categoria") CategoriaProducto categoria);

    @Query(value = """
                SELECT 
                    p.id_producto          AS idProducto,
                    p.nombre_comercial     AS nombreComercial,
                    p.nombre_generico      AS nombreGenerico,
                    p.presentacion         AS presentacion,      
                    f.nombre               AS nombreFabricante,
                    p.dosificacion         AS dosificacion,
                    p.unidad_medida        AS unidadMedida,
                    p.activo               AS activo,
                    p.stock_minimo         AS stockMinimo,
                    p.stock_maximo         AS stockMaximo,
                    p.url_foto             AS urlFoto,
            
                    -- nuevo cálculo correcto
                    COALESCE(SUM(l.stock_actual), 0) AS stockTotal
            
                FROM producto p
                LEFT JOIN fabricante f ON p.id_fabricante = f.id_fabricante
                LEFT JOIN codigo c ON c.id_producto = p.id_producto
                LEFT JOIN lote l ON l.id_codigo = c.id_codigo
            
                WHERE p.id_producto = :idProducto
            
                GROUP BY 
                    p.id_producto, p.nombre_comercial, p.nombre_generico, p.presentacion,
                    f.nombre, p.dosificacion, p.unidad_medida, p.activo,
                    p.stock_minimo, p.stock_maximo, p.url_foto
            """, nativeQuery = true)
    ProyeccionProductoDetalle buscarDetalleProducto(@Param("idProducto") String idProducto);

    @Query(value = """
            SELECT
                p.id_producto      AS idProducto,
                p.nombre_comercial AS nombreComercial,
                f.nombre           AS nombreFabricante,
                p.url_foto         AS urlFoto
            FROM producto p
            LEFT JOIN fabricante f ON p.id_fabricante = f.id_fabricante
            WHERE (:nombreComercial IS NULL OR LOWER(p.nombre_comercial) LIKE LOWER(CONCAT('%', :nombreComercial, '%')))
              AND p.activo = true
            ORDER BY p.nombre_comercial
            """, nativeQuery = true)
    List<ProyeccionProductoSimple> buscarProductosSimples(@Param("nombreComercial") String nombreComercial);

    @Query(value = """
        SELECT
            p.id_producto AS idProducto,
            COALESCE(SUM(l.stock_actual - l.stock_reservado), 0) AS stockDisponible
        FROM lote l
        JOIN codigo c   ON l.id_codigo = c.id_codigo
        JOIN producto p ON c.id_producto = p.id_producto
        WHERE l.id_lote IN :idsLotes
        GROUP BY p.id_producto
        ORDER BY p.id_producto
        """, nativeQuery = true)
    List<ProyeccionProductoStock> buscarStockPorLotes(@Param("idsLotes") List<String> idsLotes);

    @Query(
            value = """
        SELECT
            p.id_producto          AS idProducto,
            -- subconsulta: lote más próximo a vencer para ese producto + precioVenta
            (
                SELECT l2.id_lote::text
                FROM codigo c2
                JOIN lote   l2 ON l2.id_codigo = c2.id_codigo
                WHERE c2.id_producto = p.id_producto
                  AND (l2.stock_actual - l2.stock_reservado) > 0
                  AND (
                        CASE 
                            WHEN l2.porcentaje_oferta IS NOT NULL 
                                THEN ROUND(l2.precio_unitario * (1 - l2.porcentaje_oferta / 100.0))::int
                            ELSE l2.precio_unitario
                        END
                  ) = (
                        CASE 
                            WHEN l.porcentaje_oferta IS NOT NULL 
                                THEN ROUND(l.precio_unitario * (1 - l.porcentaje_oferta / 100.0))::int
                            ELSE l.precio_unitario
                        END
                  )
                ORDER BY l2.fecha_vencimiento ASC, l2.id_lote ASC
                LIMIT 1
            )                       AS idLoteMasProximoAVencer,
            
            p.nombre_comercial     AS nombreComercial,
            p.nombre_generico      AS nombreGenerico,
            f.nombre               AS nombreFabricante,
            p.dosificacion         AS dosificacion,
            p.unidad_medida        AS unidadMedida,
            p.url_foto             AS urlFoto,

            -- precio de venta efectivo (aplica porcentaje_oferta si existe)
            CASE 
                WHEN l.porcentaje_oferta IS NOT NULL 
                    THEN ROUND(l.precio_unitario * (1 - l.porcentaje_oferta / 100.0))::int
                ELSE l.precio_unitario
            END                     AS precioVenta,

            -- stock disponible (sumado) para esa combinación producto + precio
            COALESCE(SUM(l.stock_actual - l.stock_reservado), 0) AS stockTotal

        FROM producto p
        JOIN codigo c       ON c.id_producto = p.id_producto
        JOIN lote   l       ON l.id_codigo   = c.id_codigo
        LEFT JOIN fabricante f ON p.id_fabricante = f.id_fabricante

        WHERE p.activo = TRUE
          AND ( :nombreComercial IS NULL 
                OR LOWER(p.nombre_comercial) LIKE LOWER(CONCAT('%', :nombreComercial, '%')) )
          AND ( :nombreGenerico IS NULL 
                OR LOWER(p.nombre_generico) LIKE LOWER(CONCAT('%', :nombreGenerico, '%')) )
          AND ( :categoria IS NULL 
                OR p.categoria = :categoria )
          AND (l.stock_actual - l.stock_reservado) > 0  -- solo lo que se puede vender

        GROUP BY
            p.id_producto,
            p.nombre_comercial,
            p.nombre_generico,
            f.nombre,
            p.dosificacion,
            p.unidad_medida,
            p.url_foto,
            CASE 
                WHEN l.porcentaje_oferta IS NOT NULL 
                    THEN ROUND(l.precio_unitario * (1 - l.porcentaje_oferta / 100.0))::int
                ELSE l.precio_unitario
            END

        ORDER BY
            p.nombre_comercial ASC,
            precioVenta ASC
        """,
            countQuery = """
        SELECT COUNT(*) FROM (
            SELECT
                p.id_producto,
                p.nombre_comercial,
                p.nombre_generico,
                f.nombre,
                p.dosificacion,
                p.unidad_medida,
                p.url_foto,
                CASE 
                    WHEN l.porcentaje_oferta IS NOT NULL 
                        THEN ROUND(l.precio_unitario * (1 - l.porcentaje_oferta / 100.0))::int
                    ELSE l.precio_unitario
                END AS precioVenta
            FROM producto p
            JOIN codigo c       ON c.id_producto = p.id_producto
            JOIN lote   l       ON l.id_codigo   = c.id_codigo
            LEFT JOIN fabricante f ON p.id_fabricante = f.id_fabricante
            WHERE p.activo = TRUE
              AND ( :nombreComercial IS NULL 
                    OR LOWER(p.nombre_comercial) LIKE LOWER(CONCAT('%', :nombreComercial, '%')) )
              AND ( :nombreGenerico IS NULL 
                    OR LOWER(p.nombre_generico) LIKE LOWER(CONCAT('%', :nombreGenerico, '%')) )
              AND ( :categoria IS NULL 
                    OR p.categoria = :categoria )
              AND (l.stock_actual - l.stock_reservado) > 0
            GROUP BY
                p.id_producto,
                p.nombre_comercial,
                p.nombre_generico,
                f.nombre,
                p.dosificacion,
                p.unidad_medida,
                p.url_foto,
                CASE 
                    WHEN l.porcentaje_oferta IS NOT NULL 
                        THEN ROUND(l.precio_unitario * (1 - l.porcentaje_oferta / 100.0))::int
                    ELSE l.precio_unitario
                END
        ) AS conteo
        """,
            nativeQuery = true
    )
    Page<ProyeccionProductoVenta> buscarProductosParaVenta(
            @Param("nombreComercial") String nombreComercial,
            @Param("nombreGenerico") String nombreGenerico,
            @Param("categoria") String categoria,
            Pageable pageable
    );
}