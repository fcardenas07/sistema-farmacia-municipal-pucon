package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.RepositorioStock;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.ServicioStock;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioStockTest {

    private RepositorioStock repositorio;
    private ServicioStock servicio;

    @BeforeEach
    void setUp() {
        repositorio = mock(RepositorioStock.class);
        servicio = new ServicioStock(repositorio);
    }

    @Test
    @DisplayName("darBaja pone la cantidad en 0, devuelve la cantidad anterior y guarda")
    void darBajaStock() {
        var stock = new Stock();
        stock.setCantidadActual(50);

        when(repositorio.save(any(Stock.class))).thenAnswer(invocation -> invocation.getArgument(0));

        int cantidadAnterior = servicio.darBaja(stock);

        assertEquals(50, cantidadAnterior, "Debe devolver la cantidad anterior");
        assertEquals(0, stock.getCantidadActual(), "La cantidad actual debe quedar en 0");
        verify(repositorio).save(stock);
    }
}
