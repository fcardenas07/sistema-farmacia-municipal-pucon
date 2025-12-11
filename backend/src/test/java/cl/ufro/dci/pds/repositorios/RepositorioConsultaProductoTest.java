package cl.ufro.dci.pds.repositorios;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.infraestructura.ProyeccionProductoDetalle;
import cl.ufro.dci.pds.inventario.infraestructura.ProyeccionProductoFiltrado;
import cl.ufro.dci.pds.inventario.infraestructura.ProyeccionProductoSimple;
import cl.ufro.dci.pds.inventario.infraestructura.ProyeccionProductoStock;
import cl.ufro.dci.pds.inventario.infraestructura.RepositorioConsultaProducto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "/sql/repositorio-consulta-producto-test.sql")
class RepositorioConsultaProductoTest {

    @Autowired
    private RepositorioConsultaProducto repositorioConsultaProducto;

    @Test
    @DisplayName("buscarProductosConStock filtra por nombre y calcula correctamente stockTotal")
    void buscarProductosConStock_filtraYCalculaStock() {
        var resultados = repositorioConsultaProducto.buscarProductosConStock(
                "Paracetamol",
                null,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS.name()
        );

        assertThat(resultados)
                .hasSize(1);

        ProyeccionProductoFiltrado p = resultados.getFirst();
        assertThat(p.getIdProducto()).isEqualTo("prod-t-001");
        assertThat(p.getStockTotal()).isEqualTo(70); // 40 + 30
    }

    @Test
    @DisplayName("buscarProductosConStock funciona cuando categoria es NULL")
    void buscarProductosConStock_sinCategoria() {
        var resultados = repositorioConsultaProducto.buscarProductosConStock(
                "Paracetamol",
                null,
                null
        );

        assertThat(resultados)
                .extracting(ProyeccionProductoFiltrado::getIdProducto)
                .contains("prod-t-001");
    }

    @Test
    @DisplayName("buscarDetalleProducto devuelve un solo registro con stockTotal agregado")
    void buscarDetalleProducto_devuelveDetalleCorrecto() {
        ProyeccionProductoDetalle detalle =
                repositorioConsultaProducto.buscarDetalleProducto("prod-t-002");

        assertThat(detalle).isNotNull();
        assertThat(detalle.getIdProducto()).isEqualTo("prod-t-002");
        assertThat(detalle.getStockTotal()).isEqualTo(70);
    }

    @Test
    @DisplayName("buscarDetalleProducto devuelve stockTotal = 0 si no hay lotes")
    void buscarDetalleProducto_sinLotesTieneStockCero() {
        var detalle = repositorioConsultaProducto.buscarDetalleProducto("prod-sin-lote");

        assertThat(detalle).isNotNull();
        assertThat(detalle.getStockTotal()).isEqualTo(0);
    }

    @Test
    @DisplayName("buscarProductosSimples aplica filtro por nombre (case-insensitive) y ordena por nombre_comercial")
    void buscarProductosSimples_filtraYOrdena() {
        List<ProyeccionProductoSimple> lista =
                repositorioConsultaProducto.buscarProductosSimples("test");

        assertThat(lista)
                .hasSize(2);

        assertThat(lista.get(0).getNombreComercial()).isEqualTo("Ibuprofeno Test");
        assertThat(lista.get(1).getNombreComercial()).isEqualTo("Paracetamol Test");
    }

    @Test
    @DisplayName("buscarStockPorLotes agrupa por producto y calcula stockDisponible correctamente")
    void buscarStockPorLotes_calculaStockDisponible() {
        List<ProyeccionProductoStock> stocks =
                repositorioConsultaProducto.buscarStockPorLotes(List.of("lot-t-001", "lot-t-002", "lot-t-003"));

        assertThat(stocks).hasSize(2);

        var prod1 = stocks.stream()
                .filter(s -> s.getIdProducto().equals("prod-t-001"))
                .findFirst()
                .orElseThrow();

        var prod2 = stocks.stream()
                .filter(s -> s.getIdProducto().equals("prod-t-002"))
                .findFirst()
                .orElseThrow();

        // stockDisponible = stock_actual - stock_reservado por lote
        // prod-t-001: (40-5) + (30-10) = 55
        assertThat(prod1.getStockDisponible()).isEqualTo(55);

        // prod-t-002: (70-20) = 50
        assertThat(prod2.getStockDisponible()).isEqualTo(50);
    }
}
