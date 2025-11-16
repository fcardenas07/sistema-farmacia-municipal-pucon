package cl.ufro.dci.pds.repositorios;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.RepositorioProducto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
                new Producto("P001", "Paracetamol", "Paracetamol genérico", "Tabletas 500mg", "500mg", "Comprimidos", 10, 100, true),
                new Producto("P002", "Ibuprofeno", "Ibuprofeno genérico", "Tabletas 400mg", "400mg", "Comprimidos", 5, 50, true),
                new Producto("P003", "Amoxicilina", "Amoxicilina genérica", "Caja 12 cápsulas", "500mg", "mg", 20, 200, false)
        ));
    }

    @Test
    @DisplayName("buscar por campos con todos los filtros nulos devuelve todos los productos")
    void buscarProductosSinFiltros() {
        List<Producto> resultado = repositorioProducto.buscarPorCampos(null, null, null);

        assertThat(resultado).hasSize(3);
        assertThat(resultado).extracting(Producto::getIdProducto)
                .containsExactlyInAnyOrder("P001", "P002", "P003");
    }

    @Test
    @DisplayName("Buscar productos por nombre comercial devuelve coincidencias")
    void buscarProductosPorNombreComercial() {
        List<Producto> resultado = repositorioProducto.buscarPorCampos("Paracetamol", null, null);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.getFirst().getNombreComercial()).isEqualTo("Paracetamol");
    }

    @Test
    @DisplayName("Buscar productos por nombre genérico devuelve coincidencias")
    void buscarProductosPorNombreGenerico() {
        List<Producto> resultado = repositorioProducto.buscarPorCampos(null, "Ibuprofeno genérico", null);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.getFirst().getNombreGenerico()).isEqualTo("Ibuprofeno genérico");
    }

    @Test
    @DisplayName("Buscar productos por activo devuelve coincidencias")
    void buscarProductosPorActivo() {
        List<Producto> resultado = repositorioProducto.buscarPorCampos(null, null, true);

        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(Producto::getActivo);
    }

    @Test
    @DisplayName("Buscar productos sin coincidencias devuelve lista vacía")
    void buscarProductosSinCoincidencias() {
        List<Producto> resultado = repositorioProducto.buscarPorCampos("NoExiste", null, null);

        assertThat(resultado).isEmpty();
    }
}
