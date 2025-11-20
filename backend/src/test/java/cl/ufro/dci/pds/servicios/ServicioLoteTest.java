package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.inventario.app.mappers.EntradaInventarioMapper;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.RepositorioProducto;
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
        RepositorioProducto repoProducto = mock(RepositorioProducto.class);
        EntradaInventarioMapper entradaInventarioMapper = mock(EntradaInventarioMapper.class);
        servicio = new ServicioLote(repo, repoProducto, entradaInventarioMapper);
    }

    @Test
    @DisplayName("obtenerLotesDeCodigos delega correctamente al repositorio")
    void obtenerLotesDeCodigosDelegacionCorrecta() {
        List<String> idsCodigo = List.of("C1", "C2");

        var lote1 = mock(Lote.class);
        var lote2 = mock(Lote.class);
        var lotesRespuesta = List.of(lote1, lote2);

        when(repo.findByCodigo_IdCodigoIn(idsCodigo)).thenReturn(lotesRespuesta);

        List<Lote> resultado = servicio.obtenerLotesDeCodigos(idsCodigo);

        assertEquals(2, resultado.size());
        assertSame(lote1, resultado.get(0));
        assertSame(lote2, resultado.get(1));

        verify(repo, times(1)).findByCodigo_IdCodigoIn(idsCodigo);
    }

    @Test
    @DisplayName("obtenerLotesDeCodigos con lista vacía retorna lista vacía sin llamar al repositorio")
    void obtenerLotesConListaVacia() {
        List<Lote> resultado = servicio.obtenerLotesDeCodigos(List.of());

        assertTrue(resultado.isEmpty());
        verify(repo, never()).findByCodigo_IdCodigoIn(any());
    }

    @Test
    @DisplayName("obtenerLotesDeCodigos con lista nula retorna lista vacía sin llamar al repositorio")
    void obtenerLotesConListaNula() {
        var resultado = servicio.obtenerLotesDeCodigos(null);
        assertTrue(resultado.isEmpty());
        verify(repo, never()).findByCodigo_IdCodigoIn(any());
    }

    @Test
    @DisplayName("dar de baja marca el lote como INACTIVO y lo guarda")
    void darBajaLote() {
        var lote = new Lote();
        lote.setEstado("ACTIVO");

        when(repo.save(any(Lote.class))).thenAnswer(invocation -> invocation.getArgument(0));

        servicio.darBaja(lote);

        assertEquals("INACTIVO", lote.getEstado(), "El lote debe quedar inactivo");
        verify(repo).save(lote);
    }
}
