package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.*;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServicioAppProductoTest {

    private ServicioProducto servicioProducto;
    private ServicioCodigo servicioCodigo;
    private ServicioAppProducto servicioAppProducto;

    private Producto productoEntidad;
    private Codigo codigoEntidad;

    @BeforeEach
    void setUp() {
        servicioProducto = mock(ServicioProducto.class);
        servicioCodigo = mock(ServicioCodigo.class);
        servicioAppProducto = new ServicioAppProducto(servicioProducto, servicioCodigo);

        productoEntidad = new Producto(
                "P001",
                "Paracetamol",
                "Paracetamol genérico",
                "Tabletas 500mg",
                "500mg",
                "Comprimidos",
                10,
                100,
                true
        );

        codigoEntidad = new Codigo(
                "C001",
                "1234567890123",
                "EAN",
                true,
                productoEntidad
        );
    }

    @Test
    @DisplayName("Crear producto válido devuelve ProductoCreado")
    void crearProductoValido() {
        var dto = new ProductoACrear("P001", "Paracetamol", "Paracetamol genérico",
                "Tabletas 500mg", "500mg", "Comprimidos", 10, 100, true,
                List.of(new CodigoACrear("C001", "1234567890123", "EAN", true)));

        when(servicioProducto.crear(any(ProductoACrear.class))).thenReturn(productoEntidad);
        when(servicioCodigo.crear(eq(productoEntidad), any(CodigoACrear.class))).thenReturn(codigoEntidad);
        when(servicioCodigo.obtenerCodigosConIdProducto("P001")).thenReturn(List.of(codigoEntidad));

        ProductoCreado creado = servicioAppProducto.crearProducto(dto);

        assertEquals("P001", creado.idProducto());
        assertEquals("Paracetamol", creado.nombreComercial());
        assertEquals(1, creado.codigos().size());
        assertEquals("C001", creado.codigos().getFirst().idCodigo());

        verify(servicioCodigo).crear(eq(productoEntidad), any(CodigoACrear.class));
    }

    @Test
    @DisplayName("Crear producto con código duplicado lanza CodigoDuplicadoException")
    void crearProductoCodigoDuplicado() {
        var dto = new ProductoACrear("P002", "Ibuprofeno", "Ibuprofeno genérico",
                "Tabletas 400mg", "400mg", "Comprimidos", 5, 50, true,
                List.of(new CodigoACrear("C002", "9876543210987", "EAN", true)));

        when(servicioProducto.crear(any(ProductoACrear.class))).thenReturn(productoEntidad);
        when(servicioCodigo.crear(eq(productoEntidad), any(CodigoACrear.class)))
                .thenThrow(new CodigoDuplicadoException("C002"));

        assertThrows(CodigoDuplicadoException.class, () -> servicioAppProducto.crearProducto(dto));
    }

    @Test
    @DisplayName("Actualizar producto válido devuelve ProductoModificado")
    void actualizarProductoValido() {
        String id = "P001";

        var dto = new ProductoAModificar(
                "Nuevo Nombre",
                null,
                null,
                null,
                null,
                10,
                50,
                false,
                List.of(new CodigoAModificar("C001", "1234567890123", null, true))
        );

        productoEntidad.setNombreComercial("Nuevo Nombre");
        productoEntidad.setActivo(false);

        when(servicioProducto.actualizar(eq(id), any(ProductoAModificar.class))).thenReturn(productoEntidad);
        when(servicioCodigo.actualizarParaProducto(eq(id), any(CodigoAModificar.class))).thenReturn(codigoEntidad);
        when(servicioCodigo.obtenerCodigosConIdProducto(id)).thenReturn(List.of(codigoEntidad));

        ProductoModificado modificado = servicioAppProducto.actualizarProducto(id, dto);

        assertEquals("Nuevo Nombre", modificado.nombreComercial());
        assertFalse(modificado.activo());
        assertEquals(1, modificado.codigos().size());
        assertEquals("C001", modificado.codigos().getFirst().idCodigo());

        verify(servicioCodigo).actualizarParaProducto(eq(id), any(CodigoAModificar.class));
    }

    @Test
    @DisplayName("Actualizar los códigos de un producto válido devuelve ProductoModificado")
    void actualizarCodigosProductoValido() {
        String id = "P001";

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                null,
                10,
                50,
                false,
                List.of(
                        new CodigoAModificar("C001", "1234567890123", null, true),
                        new CodigoAModificar("C002", "7800987654321", null, true)
                )
        );

        productoEntidad.setNombreComercial("Nombre Nuevo");
        productoEntidad.setActivo(false);

        var codigos = List.of(
                codigoEntidad,
                new Codigo("C002", "7800987654321", "EAN", true, productoEntidad)
        );

        when(servicioProducto.actualizar(eq(id), any(ProductoAModificar.class))).thenReturn(productoEntidad);
        when(servicioCodigo.actualizarParaProducto(eq(id), any(CodigoAModificar.class))).thenReturn(codigoEntidad);
        when(servicioCodigo.obtenerCodigosConIdProducto(id)).thenReturn(codigos);

        ProductoModificado modificado = servicioAppProducto.actualizarProducto(id, dto);

        assertEquals(2, modificado.codigos().size());
        assertEquals("C001", modificado.codigos().get(0).idCodigo());
        assertEquals("C002", modificado.codigos().get(1).idCodigo());

        verify(servicioCodigo, times(2)).actualizarParaProducto(eq(id), any(CodigoAModificar.class));
    }

    @Test
    @DisplayName("Actualizar producto inexistente lanza ProductoNoEncontradoException")
    void actualizarProductoNoExistente() {
        String id = "P999";
        var dto = new ProductoAModificar("Paracetamol Modificado", "Paracetamol genérico",
                "Tabletas 500mg", "500mg", "Comprimidos", 10, 100, true,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true)));

        when(servicioProducto.actualizar(eq(id), any(ProductoAModificar.class)))
                .thenThrow(new ProductoNoEncontradoException(id));

        assertThrows(ProductoNoEncontradoException.class, () -> servicioAppProducto.actualizarProducto(id, dto));
    }

    @Test
    @DisplayName("Actualizar producto con código inexistente lanza CodigoNoEncontradoException")
    void actualizarProductoConCodigoNoExistente() {
        String id = "P001";
        var dto = new ProductoAModificar("Nombre Nuevo", null, null, null, null,
                10, 50, true,
                List.of(new CodigoAModificar("C999", "0000000000000", "EAN", true)));

        when(servicioProducto.actualizar(eq(id), any(ProductoAModificar.class))).thenReturn(productoEntidad);
        when(servicioCodigo.actualizarParaProducto(eq(id), any(CodigoAModificar.class)))
                .thenThrow(new CodigoNoEncontradoException("C999"));

        assertThrows(CodigoNoEncontradoException.class, () -> servicioAppProducto.actualizarProducto(id, dto));
    }

    @Test
    @DisplayName("Actualizar producto con código que no pertenece al producto lanza CodigoNoPerteneceProductoException")
    void actualizarProductoConCodigoNoPertenece() {
        String id = "P001";
        var dto = new ProductoAModificar("Nombre Nuevo", null, null, null, null,
                10, 50, true,
                List.of(new CodigoAModificar("C002", "7800987654321", "EAN", true)));

        when(servicioProducto.actualizar(eq(id), any(ProductoAModificar.class))).thenReturn(productoEntidad);
        when(servicioCodigo.actualizarParaProducto(eq(id), any(CodigoAModificar.class)))
                .thenThrow(new CodigoNoPerteneceProductoException("C002", id));

        assertThrows(CodigoNoPerteneceProductoException.class, () -> servicioAppProducto.actualizarProducto(id, dto));
    }

    @Test
    @DisplayName("Actualizar producto con lista de códigos nula no modifica códigos y devuelve ProductoModificado")
    void actualizarProductoConCodigosNulosNoModifica() {
        String id = "P001";
        var dto = new ProductoAModificar("Nombre Nuevo", null, null, null, null,
                10, 50, true, null);

        productoEntidad.setNombreComercial("Nombre Nuevo");
        productoEntidad.setActivo(true);

        var codigos = List.of(codigoEntidad);

        when(servicioProducto.actualizar(eq(id), any(ProductoAModificar.class))).thenReturn(productoEntidad);
        when(servicioCodigo.obtenerCodigosConIdProducto(id)).thenReturn(codigos);

        ProductoModificado modificado = servicioAppProducto.actualizarProducto(id, dto);

        assertEquals(1, modificado.codigos().size());
        assertEquals("C001", modificado.codigos().getFirst().idCodigo());

        verify(servicioCodigo, never()).actualizarParaProducto(eq(id), any(CodigoAModificar.class));
    }
}
