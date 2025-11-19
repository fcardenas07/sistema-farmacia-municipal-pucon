package cl.ufro.dci.pds.repositorios;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.RepositorioProducto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.RepositorioLote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RepositorioLoteTest {

    @Autowired
    private RepositorioLote repositorioLote;

    @Autowired
    private RepositorioProducto repositorioProducto;

    private String idParacetamol;
    private String idIbuprofeno;

    @BeforeEach
    void setUp() {
        repositorioLote.deleteAll();
        repositorioProducto.deleteAll();

        var productoParacetamol = new Producto(
                "Paracetamol", "Paracetamol",
                "Tabletas", "500", "mg",
                10, 100, true, CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS, "productos/P001.jpg"
        );

        productoParacetamol = repositorioProducto.save(productoParacetamol);
        idParacetamol = productoParacetamol.getIdProducto();

        var productoIbuprofeno = new Producto(
                "Advil", "Ibuprofeno",
                "Tabletas", "400", "mg",
                5, 50, true, CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS, "productos/P002.jpg"
        );

        productoIbuprofeno = repositorioProducto.save(productoIbuprofeno);
        idIbuprofeno = productoIbuprofeno.getIdProducto();

        var loteParacetamol1 = new Lote();
        loteParacetamol1.setProducto(productoParacetamol);
        loteParacetamol1.setNumeroLote("N001");
        loteParacetamol1.setEstado("Disponible");
        loteParacetamol1.setFechaElaboracion(LocalDate.now().minusDays(10));
        loteParacetamol1.setFechaVencimiento(LocalDate.now().plusMonths(6));

        var loteParacetamol2 = new Lote();
        loteParacetamol2.setProducto(productoParacetamol);
        loteParacetamol2.setNumeroLote("N002");
        loteParacetamol2.setEstado("Disponible");
        loteParacetamol2.setFechaElaboracion(LocalDate.now().minusDays(5));
        loteParacetamol2.setFechaVencimiento(LocalDate.now().plusMonths(6));

        var loteIbuprofeno = new Lote();
        loteIbuprofeno.setProducto(productoIbuprofeno);
        loteIbuprofeno.setNumeroLote("N003");
        loteIbuprofeno.setEstado("Disponible");
        loteIbuprofeno.setFechaElaboracion(LocalDate.now().minusDays(15));
        loteIbuprofeno.setFechaVencimiento(LocalDate.now().plusMonths(12));

        var stockParacetamol1 = new Stock();
        stockParacetamol1.setCantidadActual(50);
        stockParacetamol1.setLote(loteParacetamol1);
        loteParacetamol1.setStock(stockParacetamol1);

        var stockParacetamol2 = new Stock();
        stockParacetamol2.setCantidadActual(50);
        stockParacetamol2.setLote(loteParacetamol2);
        loteParacetamol2.setStock(stockParacetamol2);

        var stockIbuprofeno = new Stock();
        stockIbuprofeno.setCantidadActual(50);
        stockIbuprofeno.setLote(loteIbuprofeno);
        loteIbuprofeno.setStock(stockIbuprofeno);

        repositorioLote.saveAll(List.of(loteParacetamol1, loteParacetamol2, loteIbuprofeno));
    }

    @Test
    @DisplayName("Buscar lotes de un producto existente devuelve todos los lotes y carga el stock")
    void obtenerLotesDeProductoExistenteConStock() {
        var lotesParacetamol = repositorioLote.findByProducto_IdProductoIn(List.of(idParacetamol));

        assertThat(lotesParacetamol).hasSize(2);
        assertThat(lotesParacetamol).allMatch(lote -> lote.getProducto().getIdProducto().equals(idParacetamol));
        assertThat(lotesParacetamol).allMatch(lote -> lote.getStock() != null);
        assertThat(lotesParacetamol).extracting(lote -> lote.getStock().getCantidadActual())
                .containsExactlyInAnyOrder(50, 50);
    }

    @Test
    @DisplayName("Buscar lotes de varios productos devuelve todos los lotes correspondientes y carga el stock")
    void obtenerLotesDeMultiplesProductos() {
        var lotesTodosProductos = repositorioLote.findByProducto_IdProductoIn(List.of(idParacetamol, idIbuprofeno));

        assertThat(lotesTodosProductos).hasSize(3);
        assertThat(lotesTodosProductos).extracting(lote -> lote.getProducto().getIdProducto())
                .containsExactlyInAnyOrder(idParacetamol, idParacetamol, idIbuprofeno);
        assertThat(lotesTodosProductos).allMatch(lote -> lote.getStock() != null);
    }

    @Test
    @DisplayName("Buscar lotes con lista de IDs vacía devuelve lista vacía")
    void buscarLotesConListaVacia() {
        var lotesVacios = repositorioLote.findByProducto_IdProductoIn(List.of());
        assertThat(lotesVacios).isEmpty();
    }

    @Test
    @DisplayName("Buscar lotes con IDs inexistentes devuelve lista vacía")
    void buscarLotesConIdsInexistentes() {
        var lotesInexistentes = repositorioLote.findByProducto_IdProductoIn(List.of("NO_EXISTE"));
        assertThat(lotesInexistentes).isEmpty();
    }
}
