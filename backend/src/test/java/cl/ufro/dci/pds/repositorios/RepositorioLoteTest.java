package cl.ufro.dci.pds.repositorios;

import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.RepositorioCodigo;
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
    private RepositorioCodigo repositorioCodigo;

    @Autowired
    private RepositorioProducto repositorioProducto;

    private String idCodigoParacetamol;
    private String idCodigoIbuprofeno;
    private String idParacetamol;
    private String idIbuprofeno;

    @BeforeEach
    void setUp() {
        repositorioLote.deleteAll();
        repositorioProducto.deleteAll();
        repositorioCodigo.deleteAll();

        var productoParacetamol = new Producto(
                "Paracetamol", "Paracetamol",
                "Tabletas", 500, "mg",
                10, 100, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "productos/P001.jpg"
        );
        productoParacetamol = repositorioProducto.save(productoParacetamol);
        idParacetamol = productoParacetamol.getIdProducto();

        var productoIbuprofeno = new Producto(
                "Advil", "Ibuprofeno",
                "Tabletas", 400, "mg",
                5, 50, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "productos/P002.jpg"
        );
        productoIbuprofeno = repositorioProducto.save(productoIbuprofeno);
        idIbuprofeno = productoIbuprofeno.getIdProducto();

        var codigoParacetamol = repositorioCodigo.save(new Codigo("C001", "EAN", true, productoParacetamol));
        var codigoIbuprofeno = repositorioCodigo.save(new Codigo("C002", "EAN", true, productoIbuprofeno));

        idCodigoParacetamol = codigoParacetamol.getIdCodigo();
        idCodigoIbuprofeno = codigoIbuprofeno.getIdCodigo();

        var loteParacetamol1 = crearLoteConStock(codigoParacetamol, "N001");
        var loteParacetamol2 = crearLoteConStock(codigoParacetamol, "N002");
        var loteIbuprofeno = crearLoteConStock(codigoIbuprofeno, "N003");

        repositorioLote.saveAll(List.of(loteParacetamol1, loteParacetamol2, loteIbuprofeno));
    }

    private Lote crearLoteConStock(Codigo codigo, String numeroLote) {
        var lote = new Lote();
        lote.setCodigo(codigo);
        lote.setNumeroLote(numeroLote);
        lote.setEstado("Disponible");
        lote.setFechaElaboracion(LocalDate.now().minusDays(5));
        lote.setFechaVencimiento(LocalDate.now().plusMonths(6));

        var stock = new Stock();
        stock.setCantidadActual(50);
        stock.setLote(lote);
        lote.setStock(stock);

        return lote;
    }

    @Test
    @DisplayName("Buscar lotes de un producto existente devuelve todos los lotes y carga el stock")
    void obtenerLotesDeProductoExistenteConStock() {
        var lotesParacetamol = repositorioLote.findByCodigo_IdCodigoIn(List.of(idCodigoParacetamol));

        assertThat(lotesParacetamol).hasSize(2);
        assertThat(lotesParacetamol).allMatch(lote -> lote.getCodigo().getProducto().getIdProducto().equals(idParacetamol));
        assertThat(lotesParacetamol).allMatch(lote -> lote.getStock() != null);
        assertThat(lotesParacetamol).extracting(lote -> lote.getStock().getCantidadActual())
                .containsExactlyInAnyOrder(50, 50);
    }

    @Test
    @DisplayName("Buscar lotes de varios productos devuelve todos los lotes correspondientes y carga el stock")
    void obtenerLotesDeMultiplesProductos() {
        var lotesTodosProductos = repositorioLote.findByCodigo_IdCodigoIn(List.of(idCodigoParacetamol, idCodigoIbuprofeno));

        assertThat(lotesTodosProductos).hasSize(3);
        assertThat(lotesTodosProductos).extracting(lote -> lote.getCodigo().getProducto().getIdProducto())
                .containsExactlyInAnyOrder(idParacetamol, idParacetamol, idIbuprofeno);
        assertThat(lotesTodosProductos).allMatch(lote -> lote.getStock() != null);
    }

    @Test
    @DisplayName("Buscar lotes con lista de IDs vacía devuelve lista vacía")
    void buscarLotesConListaVacia() {
        var lotesVacios = repositorioLote.findByCodigo_IdCodigoIn(List.of());
        assertThat(lotesVacios).isEmpty();
    }

    @Test
    @DisplayName("Buscar lotes con IDs inexistentes devuelve lista vacía")
    void buscarLotesConIdsInexistentes() {
        var lotesInexistentes = repositorioLote.findByCodigo_IdCodigoIn(List.of("NO_EXISTE"));
        assertThat(lotesInexistentes).isEmpty();
    }
}
