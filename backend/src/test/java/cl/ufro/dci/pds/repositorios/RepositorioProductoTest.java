package cl.ufro.dci.pds.repositorios;

import cl.ufro.dci.pds.fixtures.builders.ProductoBuilder;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.RepositorioProducto;
import cl.ufro.dci.pds.fixtures.mothers.ProductoMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RepositorioProductoTest {

    @Autowired
    private RepositorioProducto repositorioProducto;

    @BeforeEach
    void setUp() {
        repositorioProducto.deleteAll();
        repositorioProducto.save(ProductoMother.paracetamolSinId());
    }

    @Test
    @DisplayName("existe por clave única devuelve true si existe un producto con la misma clave")
    void existsByClaveUnicaDevuelveTrue() {
        var producto = repositorioProducto.findAll().getFirst();

        boolean existe = repositorioProducto.existsByClaveUnica(
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                producto.getPresentacion(),
                producto.getDosificacion(),
                producto.getUnidadMedida(),
                producto.getFabricante() != null ? producto.getFabricante().getIdFabricante() : null
        );

        assertThat(existe).isTrue();
    }

    @Test
    @DisplayName("existe por clave única devuelve false si no existe un producto con la misma clave")
    void existsByClaveUnicaDevuelveFalse() {
        var producto = ProductoMother.amoxicilinaSinId();

        boolean existe = repositorioProducto.existsByClaveUnica(
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                producto.getPresentacion(),
                producto.getDosificacion(),
                producto.getUnidadMedida(),
                producto.getFabricante() != null ? producto.getFabricante().getIdFabricante() : null
        );

        assertThat(existe).isFalse();
    }

    @Test
    @DisplayName("existe por clave única funciona correctamente con presentacion NULL")
    void existsConPresentacionNull() {
        var producto = new ProductoBuilder()
                .conNombreComercial("Prod X")
                .stock(10, 100)
                .sinFabricante()
                .build();

        producto.setPresentacion(null);
        repositorioProducto.save(producto);

        boolean existe = repositorioProducto.existsByClaveUnica(
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                null,
                producto.getDosificacion(),
                producto.getUnidadMedida(),
                null
        );

        assertThat(existe).isTrue();
    }

    @Test
    @DisplayName("existe por clave única funciona con unidadMedida NULL")
    void existsConUnidadMedidaNull() {
        var producto = new ProductoBuilder()
                .conNombreComercial("Producto Y")
                .sinFabricante()
                .build();

        producto.setUnidadMedida(null);
        repositorioProducto.save(producto);

        boolean existe = repositorioProducto.existsByClaveUnica(
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                producto.getPresentacion(),
                producto.getDosificacion(),
                null,
                null
        );

        assertThat(existe).isTrue();
    }

    @Test
    @DisplayName("existe por clave única devuelve no existe si cambia presentación aunque los demás campos coincidan")
    void noExisteSiPresentacionDifiere() {
        var producto = new ProductoBuilder().build();
        repositorioProducto.save(producto);

        var existe = repositorioProducto.existsByClaveUnica(
                producto.getNombreComercial(),
                producto.getNombreGenerico(),
                "otra presentacion",
                producto.getDosificacion(),
                producto.getUnidadMedida(),
                producto.getFabricante() != null ? producto.getFabricante().getIdFabricante() : null
        );

        assertThat(existe).isFalse();
    }

    @Test
    @DisplayName("JPA asigna ID al guardar un producto sin ID")
    void asignaIdAlGuardar() {
        var producto = ProductoMother.paracetamolSinId();
        var guardado = repositorioProducto.save(producto);
        assertThat(guardado.getIdProducto()).isNotNull();
    }
}
