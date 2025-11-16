package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.ProductoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioProductoTest {

    private RepositorioProducto repositorioProducto;
    private ServicioProducto servicioProducto;

    private Producto productoEntidad;

    @BeforeEach
    void setUp() {
        repositorioProducto = mock(RepositorioProducto.class);
        servicioProducto = new ServicioProducto(repositorioProducto);

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
    }

    @Test
    @DisplayName("Crear producto válido guarda y devuelve el producto")
    void crearProductoValido() {
        var dto = new ProductoACrear(
                "P001",
                "Paracetamol",
                "Paracetamol genérico",
                "Tabletas 500mg",
                "500mg",
                "Comprimidos",
                10,
                100,
                true,
                null
        );

        when(repositorioProducto.existsById("P001")).thenReturn(false);
        when(repositorioProducto.save(any(Producto.class))).thenReturn(productoEntidad);

        Producto creado = servicioProducto.crear(dto);

        assertEquals("P001", creado.getIdProducto());
        assertEquals("Paracetamol", creado.getNombreComercial());

        verify(repositorioProducto).existsById("P001");
        verify(repositorioProducto).save(any(Producto.class));
    }

    @Test
    @DisplayName("Crear producto duplicado lanza ProductoDuplicadoException")
    void crearProductoDuplicado() {
        var dto = new ProductoACrear(
                "P001",
                "Paracetamol",
                "Paracetamol genérico",
                "Tabletas 500mg",
                "500mg",
                "Comprimidos",
                10,
                100,
                true,
                null
        );

        when(repositorioProducto.existsById("P001")).thenReturn(true);

        assertThrows(ProductoDuplicadoException.class, () -> servicioProducto.crear(dto));

        verify(repositorioProducto).existsById("P001");
        verify(repositorioProducto, never()).save(any());
    }

    @Test
    @DisplayName("Actualizar producto válido modifica y guarda el producto")
    void actualizarProductoValido() {
        var dto = new ProductoAModificar(
                "Nuevo Nombre",
                null,
                null,
                null,
                null,
                20,
                200,
                false,
                null
        );

        when(repositorioProducto.findById("P001")).thenReturn(Optional.of(productoEntidad));
        when(repositorioProducto.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto actualizado = servicioProducto.actualizar("P001", dto);

        assertEquals("Nuevo Nombre", actualizado.getNombreComercial());
        assertEquals(20, actualizado.getStockMinimo());
        assertFalse(actualizado.getActivo());

        verify(repositorioProducto).findById("P001");
        verify(repositorioProducto).save(any(Producto.class));
    }

    @Test
    @DisplayName("Actualizar producto inexistente lanza ProductoNoEncontradoException")
    void actualizarProductoNoExistente() {
        var dto = new ProductoAModificar(
                "Nuevo Nombre",
                null,
                null,
                null,
                null,
                20,
                200,
                false,
                null
        );

        when(repositorioProducto.findById("P999")).thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontradoException.class, () -> servicioProducto.actualizar("P999", dto));

        verify(repositorioProducto).findById("P999");
        verify(repositorioProducto, never()).save(any());
    }
}
