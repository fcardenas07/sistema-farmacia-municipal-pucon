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
                new Producto("Paracetamol", "Paracetamol",
                        "Tabletas", "500", "mg",
                        10, 100, true, CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                        "productos/P001.jpg"),
                new Producto("Advil", "Ibuprofeno",
                        "Tabletas", "400", "mg",
                        5, 50, true, CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                        "productos/P002.jpg"),
                new Producto("Amoxil", "Amoxicilina",
                        "Caja 12 cápsulas", "500", "mg",
                        20, 200, false, CategoriaProducto.ANTIBIOTICOS,
                        "productos/P003.jpg")
        ));
    }

    @Test
    @DisplayName("buscar por campos con todos los filtros nulos devuelve todos los productos")
    void buscarProductosSinFiltros() {
        var pageable = PageRequest.of(0, 4);
        var resultado = repositorioProducto.buscarPorCampos(null, null, null, null, pageable);

        assertThat(resultado).hasSize(3);
        assertThat(resultado).extracting(Producto::getNombreComercial)
                .containsExactlyInAnyOrder("Paracetamol", "Advil", "Amoxil");
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
        var resultado = repositorioProducto.buscarPorCampos(null, "Ibuprofeno", null, null, pageable);

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().getFirst().getNombreGenerico()).isEqualTo("Ibuprofeno");
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
                .allMatch(p -> p.getIdProducto() != null && !p.getIdProducto().isBlank());
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
