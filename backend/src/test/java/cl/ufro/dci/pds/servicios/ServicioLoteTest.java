package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.RepositorioLote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioLoteTest {

    private RepositorioLote repo;
    private ServicioLote servicio;

    @BeforeEach
    void setUp() {
        repo = mock(RepositorioLote.class);
        servicio = new ServicioLote(repo);
    }

    @Test
    @DisplayName("obtenerLotesDeProductos delega correctamente al repositorio")
    void obtenerLotesDeProductosDelegacionCorrecta() {
        List<String> ids = List.of("P1", "P2");

        var lote1 = mock(Lote.class);
        var lote2 = mock(Lote.class);
        var lotesRespuesta = List.of(lote1, lote2);

        when(repo.findByProducto_IdProductoIn(ids)).thenReturn(lotesRespuesta);

        List<Lote> resultado = servicio.obtenerLotesDeProductos(ids);

        assertEquals(2, resultado.size());
        assertSame(lote1, resultado.get(0));
        assertSame(lote2, resultado.get(1));

        verify(repo, times(1)).findByProducto_IdProductoIn(ids);
    }

    @Test
    @DisplayName("obtenerLotesDeProductos con lista vacía retorna lista vacía sin llamar al repositorio")
    void obtenerLotesConListaVacia() {
        List<Lote> resultado = servicio.obtenerLotesDeProductos(List.of());

        assertTrue(resultado.isEmpty());
        verify(repo, never()).findByProducto_IdProductoIn(any());
    }

    @Test
    @DisplayName("obtenerLotesDeProductos con lista nula retorna lista vacía sin llamar al repositorio")
    void obtenerLotesConListaNula() {
        var resultado = servicio.obtenerLotesDeProductos(null);
        assertTrue(resultado.isEmpty());
        verify(repo, never()).findByProducto_IdProductoIn(any());
    }
}
