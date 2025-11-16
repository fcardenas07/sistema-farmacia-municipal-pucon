package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.ProductoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioProductoTest {

    private RepositorioProducto repositorioProducto;
    private ServicioProducto servicioProducto;

    private Producto productoEntidad;
    private List<Producto> productosFiltrados;


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

        productosFiltrados = List.of(
                new Producto("P001","Paracetamol","Paracetamol genérico","Tabletas 500mg","500mg","Comprimidos",10,100,true),
                new Producto("P002","Ibuprofeno","Ibuprofeno genérico","Tabletas 400mg","400mg","Comprimidos",5,50,true),
                new Producto("P003","Amoxicilina","Amoxicilina genérica","Caja 12 cápsulas","500mg","mg",20,200,false)
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

    @Test
    @DisplayName("Buscar productos sin filtros devuelve todos los productos")
    void buscarProductosSinFiltros() {
        when(repositorioProducto.buscarPorCampos(null, null, null)).thenReturn(productosFiltrados);

        var resultado = servicioProducto.buscarPorCampos(null, null, null);

        assertEquals(3, resultado.size());
        assertTrue(resultado.stream().anyMatch(p -> p.getIdProducto().equals("P001")));
        assertTrue(resultado.stream().anyMatch(p -> p.getIdProducto().equals("P002")));
        assertTrue(resultado.stream().anyMatch(p -> p.getIdProducto().equals("P003")));
    }

    @Test
    @DisplayName("Buscar productos por nombreComercial devuelve coincidencias")
    void buscarProductosPorNombreComercial() {
        when(repositorioProducto.buscarPorCampos("Paracetamol", null, null))
                .thenReturn(List.of(productosFiltrados.getFirst()));

        var resultado = servicioProducto.buscarPorCampos("Paracetamol", null, null);

        assertEquals(1, resultado.size());
        assertEquals("Paracetamol", resultado.getFirst().getNombreComercial());
    }

    @Test
    @DisplayName("Buscar productos por nombreGenerico devuelve coincidencias")
    void buscarProductosPorNombreGenerico() {
        when(repositorioProducto.buscarPorCampos(null, "Ibuprofeno genérico", null))
                .thenReturn(List.of(productosFiltrados.get(1)));

        var resultado = servicioProducto.buscarPorCampos(null, "Ibuprofeno genérico", null);

        assertEquals(1, resultado.size());
        assertEquals("Ibuprofeno genérico", resultado.getFirst().getNombreGenerico());
    }

    @Test
    @DisplayName("Buscar productos por activo devuelve coincidencias")
    void buscarProductosPorActivo() {
        when(repositorioProducto.buscarPorCampos(null, null, true))
                .thenReturn(productosFiltrados.stream().filter(Producto::getActivo).toList());

        var resultado = servicioProducto.buscarPorCampos(null, null, true);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(Producto::getActivo));
    }

    @Test
    @DisplayName("Buscar productos sin coincidencias devuelve lista vacía")
    void buscarProductosSinCoincidencias() {
        when(repositorioProducto.buscarPorCampos("X", null, null))
                .thenReturn(List.of());

        var resultado = servicioProducto.buscarPorCampos("X", null, null);

        assertTrue(resultado.isEmpty());
    }

}
