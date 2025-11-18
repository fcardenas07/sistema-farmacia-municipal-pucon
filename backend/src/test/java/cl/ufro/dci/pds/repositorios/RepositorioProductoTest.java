package cl.ufro.dci.pds.repositorios;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.RepositorioProducto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RepositorioProductoTest {

    @Autowired
    private RepositorioProducto repositorioProducto;

    @BeforeEach
    void setUp() {
        repositorioProducto.deleteAll();

        repositorioProducto.saveAll(List.of(
                new Producto("P001", "Paracetamol", "Paracetamol genérico",
                        "Tabletas 500mg", "500mg", "Comprimidos",
                        10, 100, true, CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                        "producto/P001.jpg"),
                new Producto("P002", "Ibuprofeno", "Ibuprofeno genérico",
                        "Tabletas 400mg", "400mg", "Comprimidos",
                        5, 50, true, CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                        "producto/P002.jpg"),
                new Producto("P003", "Amoxicilina", "Amoxicilina genérica",
                        "Caja 12 cápsulas", "500mg", "mg",
                        20, 200, false, CategoriaProducto.ANTIBIOTICOS,
                        "producto/P003.jpg")
        ));
    }

    @Test
    @DisplayName("buscar por campos con todos los filtros nulos devuelve todos los productos")
    void buscarProductosSinFiltros() {
        var pageable = PageRequest.of(0, 4);
        var resultado = repositorioProducto.buscarPorCampos(null, null, null, null, pageable);

        assertThat(resultado).hasSize(3);
        assertThat(resultado).extracting(Producto::getIdProducto)
                .containsExactlyInAnyOrder("P001", "P002", "P003");
    }

    @Test
    @DisplayName("Buscar productos por nombre comercial devuelve coincidencias")
    void buscarProductosPorNombreComercial() {
        var pageable = PageRequest.of(0, 4);
        var resultado = repositorioProducto.buscarPorCampos("Paracetamol", null, null, null, pageable);

        assertThat(resultado.getContent()).hasSize(1);

        assertThat(resultado.getContent().getFirst().getNombreComercial()).isEqualTo("Paracetamol");
    }

    @Test
    @DisplayName("Buscar productos por nombre genérico devuelve coincidencias")
    void buscarProductosPorNombreGenerico() {
        var pageable = PageRequest.of(0, 4);
        var resultado = repositorioProducto.buscarPorCampos(null, "Ibuprofeno genérico", null, null, pageable);

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().getFirst().getNombreGenerico()).isEqualTo("Ibuprofeno genérico");
    }

    @Test
    @DisplayName("Buscar productos por categoría devuelve coincidencias")
    void buscarProductosPorCategoria() {
        var pageable = PageRequest.of(0, 4);
        var resultado = repositorioProducto.buscarPorCampos(
                null,
                null,
                null,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                pageable
        );

        assertThat(resultado.getContent()).hasSize(2);

        assertThat(resultado.getContent())
                .allMatch(p -> p.getCategoriaProducto() == CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS);

        assertThat(resultado.getContent())
                .extracting(Producto::getIdProducto)
                .containsExactlyInAnyOrder("P001", "P002");
    }

    @Test
    @DisplayName("Buscar productos por activo devuelve coincidencias")
    void buscarProductosPorActivo() {
        var pageable = PageRequest.of(0, 4);
        var resultado = repositorioProducto.buscarPorCampos(null, null, true, null, pageable);

        assertThat(resultado.getContent()).hasSize(2);
        assertThat(resultado.getContent()).allMatch(Producto::isActivo);
    }

    @Test
    @DisplayName("Buscar productos sin coincidencias devuelve lista vacía")
    void buscarProductosSinCoincidencias() {
        var pageable = PageRequest.of(0, 4);
        var resultado = repositorioProducto.buscarPorCampos("NoExiste", null, null, null, pageable);

        assertThat(resultado.getContent()).isEmpty();
    }
}
