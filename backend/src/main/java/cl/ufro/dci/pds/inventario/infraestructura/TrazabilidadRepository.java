package cl.ufro.dci.pds.inventario.infraestructura;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrazabilidadRepository extends JpaRepository<Lote, String> {

    @Query(
            value = """
    SELECT
        c.id_codigo          AS idCodigo,
        p.nombre_comercial   AS nombreComercial,
        l.numero_lote        AS codigoLote,
        l.fecha_elaboracion  AS fechaElaboracion,
        l.fecha_vencimiento  AS fechaVencimiento,
        s.cantidad_actual    AS cantidad,
        l.estado             AS estado,
        m.fecha_movimiento   AS ultimoMovimiento,
        m.tipo_movimiento    AS tipoMovimiento
    FROM lote l
    JOIN codigo c    ON c.id_codigo = l.id_codigo
    JOIN producto p  ON p.id_producto = c.id_producto
    JOIN stock s     ON s.id_lote = l.id_lote

    LEFT JOIN (
        SELECT m2.id_lote, m2.fecha_movimiento, m2.tipo_movimiento
        FROM movimiento m2
        WHERE m2.fecha_movimiento = (
            SELECT MAX(m3.fecha_movimiento)
            FROM movimiento m3
            WHERE m3.id_lote = m2.id_lote
        )
    ) m ON m.id_lote = l.id_lote

    ORDER BY l.fecha_elaboracion DESC
    """,
            nativeQuery = true
    )
    List<TrazabilidadProjection> getTrazabilidadDeTodosLosLotes();

}
