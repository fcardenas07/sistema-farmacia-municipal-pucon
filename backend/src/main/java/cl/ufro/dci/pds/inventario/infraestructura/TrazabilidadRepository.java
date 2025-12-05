package cl.ufro.dci.pds.inventario.infraestructura;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrazabilidadRepository extends JpaRepository<Lote, String> {

    @Query(
            value = """
                    SELECT
                        c.id_codigo          AS idCodigo,
                        p.nombre_comercial   AS nombreComercial,
                        l.numero_lote        AS numeroLote,
                        l.fecha_elaboracion  AS fechaElaboracion,
                        l.fecha_vencimiento  AS fechaVencimiento,
                    
                        l.stock_actual       AS cantidad,
                    
                        l.estado             AS estado,
                        m.fecha_movimiento   AS ultimoMovimiento,
                        m.tipo_movimiento    AS tipoMovimiento
                    
                    FROM lote l
                    JOIN codigo c   ON c.id_codigo = l.id_codigo
                    JOIN producto p ON p.id_producto = c.id_producto
                    
                    LEFT JOIN (
                        SELECT m2.id_lote, m2.fecha_movimiento, m2.tipo_movimiento
                        FROM movimiento m2
                        WHERE m2.fecha_movimiento = (
                            SELECT MAX(m3.fecha_movimiento)
                            FROM movimiento m3
                            WHERE m3.id_lote = m2.id_lote
                        )
                    ) m ON m.id_lote = l.id_lote
                    
                    ORDER BY m.fecha_movimiento DESC
                    """,
            nativeQuery = true
    )
    List<TrazabilidadIngresoProjection> getTrazabilidadDeTodosLosLotes();

    @Query(
            value = """
                    SELECT
                        c.id_codigo          AS idCodigo,
                        p.nombre_comercial   AS nombreComercial,
                        l.numero_lote        AS numeroLote,
                        l.fecha_elaboracion  AS fechaElaboracion,
                        l.fecha_vencimiento  AS fechaVencimiento,
                        m.cantidad           AS cantidad,
                        l.estado             AS estado,
                        m.fecha_movimiento   AS fechaMovimiento,
                        m.tipo_movimiento    AS tipoMovimiento
                    FROM movimiento m
                    JOIN lote l      ON m.id_lote = l.id_lote
                    JOIN codigo c    ON l.id_codigo = c.id_codigo
                    JOIN producto p  ON c.id_producto = p.id_producto
                    WHERE m.tipo_movimiento = 'INGRESO'
                    ORDER BY m.fecha_movimiento DESC
                    """,
            nativeQuery = true
    )
    Page<TrazabilidadIngresoProjection> getIngresosOrdenados(Pageable pageable);
}
