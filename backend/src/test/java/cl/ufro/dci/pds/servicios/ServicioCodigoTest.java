package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.CodigoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.CodigoAModificar;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.*;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioCodigoTest {

    private RepositorioCodigo repositorioCodigo;
    private ServicioCodigo servicioCodigo;

    private Producto productoEntidad;
    private Codigo codigoEntidad;

    @BeforeEach
    void setUp() {
        repositorioCodigo = mock(RepositorioCodigo.class);
        servicioCodigo = new ServicioCodigo(repositorioCodigo);

        productoEntidad = new Producto(
                "Paracetamol",
                "Paracetamol",
                "30 Comprimidos",
                "500",
                "mg",
                10,
                100,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0001.jpg"
        );

        codigoEntidad = new Codigo(
                "1234567890123",
                "EAN",
                true,
                productoEntidad
        );

        ReflectionTestUtils.setField(codigoEntidad, "idCodigo", "C001");
        ReflectionTestUtils.setField(productoEntidad, "idProducto", "P001");
    }

    @Test
    @DisplayName("Crear código válido guarda y devuelve el código")
    void crearCodigoValido() {
        var dto = new CodigoACrear("1234567890123", "EAN", true);

        when(repositorioCodigo.existsById("C001")).thenReturn(false);
        when(repositorioCodigo.save(any(Codigo.class))).thenReturn(codigoEntidad);

        var creado = servicioCodigo.crear(productoEntidad, dto);

        assertEquals("C001", creado.getIdCodigo());
        assertEquals("EAN", creado.getTipoCodigo());
        assertEquals(productoEntidad, creado.getProducto());

        verify(repositorioCodigo).save(any(Codigo.class));
    }

    @Test
    @DisplayName("Actualizar código válido devuelve el código actualizado")
    void actualizarCodigoValido() {
        var dto = new CodigoAModificar("C001", "1234567890123", "EAN", true);

        when(repositorioCodigo.existsById("C001")).thenReturn(true);
        when(repositorioCodigo.findByIdCodigoAndProducto_IdProducto("C001", "P001"))
                .thenReturn(Optional.of(codigoEntidad));
        when(repositorioCodigo.save(any(Codigo.class))).thenReturn(codigoEntidad);

        Codigo actualizado = servicioCodigo.actualizarParaProducto("P001", dto);

        assertEquals("C001", actualizado.getIdCodigo());
        verify(repositorioCodigo).save(any(Codigo.class));
    }

    @Test
    @DisplayName("Actualizar código inexistente lanza CodigoNoEncontradoException")
    void actualizarCodigoNoExistente() {
        var dto = new CodigoAModificar("C999", "0000000000000", "EAN", true);

        when(repositorioCodigo.existsById("C999")).thenReturn(false);

        assertThrows(CodigoNoEncontradoException.class, () -> servicioCodigo.actualizarParaProducto("P001", dto));

        verify(repositorioCodigo).existsById("C999");
        verify(repositorioCodigo, never()).save(any());
    }

    @Test
    @DisplayName("Actualizar código que no pertenece al producto lanza CodigoNoPerteneceProductoException")
    void actualizarCodigoNoPertenece() {
        var dto = new CodigoAModificar("C002", "7800987654321", "EAN", true);

        when(repositorioCodigo.existsById("C002")).thenReturn(true);
        when(repositorioCodigo.findByIdCodigoAndProducto_IdProducto("C002", "P001"))
                .thenReturn(Optional.empty());

        assertThrows(CodigoNoPerteneceProductoException.class, () -> servicioCodigo.actualizarParaProducto("P001", dto));

        verify(repositorioCodigo).existsById("C002");
        verify(repositorioCodigo, never()).save(any());
    }

    @Test
    @DisplayName("Obtener códigos por producto devuelve la lista de códigos")
    void obtenerCodigosPorProducto() {
        when(repositorioCodigo.findAllByProducto_IdProducto("P001"))
                .thenReturn(List.of(codigoEntidad));

        List<Codigo> codigos = servicioCodigo.obtenerCodigosConIdProducto("P001");

        assertEquals(1, codigos.size());
        assertEquals("C001", codigos.getFirst().getIdCodigo());
        verify(repositorioCodigo).findAllByProducto_IdProducto("P001");
    }

    @Test
    @DisplayName("Obtener códigos por varios productos devuelve los códigos correctos")
    void obtenerCodigosPorVariosProductos() {
        var producto2 = new Producto(
                "Ibuprofeno",
                "Ibuprofeno Genérico",
                "20 Comprimidos",
                "400",
                "mg",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0002.jpg"
        );

        ReflectionTestUtils.setField(producto2, "idProducto", "P002");

        var codigo2 = new Codigo("9876543210987", "EAN", true, producto2);
        ReflectionTestUtils.setField(codigo2, "idCodigo", "C002");

        when(repositorioCodigo.findAllByProducto_IdProductoIn(List.of("P001", "P002")))
                .thenReturn(List.of(codigoEntidad, codigo2));

        var codigos = servicioCodigo.obtenerCodigosConIdProductoEn(List.of("P001", "P002"));

        assertEquals(2, codigos.size());
        assertTrue(codigos.contains(codigoEntidad));
        assertTrue(codigos.contains(codigo2));

        verify(repositorioCodigo).findAllByProducto_IdProductoIn(List.of("P001", "P002"));
    }

    @Test
    @DisplayName("dar de baja marca el código como inactivo y lo guarda")
    void darBajaCodigo() {
        codigoEntidad.setActivo(true);

        when(repositorioCodigo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        servicioCodigo.darBaja(codigoEntidad);

        assertFalse(codigoEntidad.isActivo(), "El código debe quedar inactivo");
        verify(repositorioCodigo).save(codigoEntidad);
    }
}
