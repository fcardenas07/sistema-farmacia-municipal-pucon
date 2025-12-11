package cl.ufro.dci.pds.fixtures.mothers;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public class LoteMother {

    public static Lote base() {
        var lote = new Lote();
        lote.setFechaElaboracion(LocalDateTime.now().minusMonths(2));
        lote.setFechaVencimiento(LocalDateTime.now().plusMonths(10));
        lote.setNumeroLote("L001");
        lote.setEstado("DISPONIBLE");
        lote.setPrecioUnitario(1000);
        lote.setStockInicial(100);
        lote.setStockActual(100);
        lote.setStockReservado(0);

        var codigo = new Codigo();
        ReflectionTestUtils.setField(codigo, "idCodigo", "COD001");
        lote.setCodigo(codigo);

        return lote;
    }

    public static Lote conStock(int inicial, int actual, int reservado) {
        var lote = base();
        lote.setStockInicial(inicial);
        lote.setStockActual(actual);
        lote.setStockReservado(reservado);
        return lote;
    }

    public static Lote vencido() {
        var lote = base();
        lote.setFechaVencimiento(LocalDateTime.now().minusDays(5));
        return lote;
    }

    public static Lote sinStock() {
        return conStock(0, 0, 0);
    }
}
