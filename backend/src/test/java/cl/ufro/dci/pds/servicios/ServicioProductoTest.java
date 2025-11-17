package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.infraestructura.ImagenAlmacenadaException;
import cl.ufro.dci.pds.infraestructura.ServicioAlmacenamientoImagen;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioProductoTest {

    private RepositorioProducto repositorioProducto;
    private ServicioAlmacenamientoImagen servicioAlmacenamientoImagen;
    private ServicioProducto servicioProducto;

    private Producto productoEntidad;
    private List<Producto> productosFiltrados;

    @BeforeEach
    void setUp() {
        repositorioProducto = mock(RepositorioProducto.class);
        servicioAlmacenamientoImagen = mock(ServicioAlmacenamientoImagen.class);
        servicioProducto = new ServicioProducto(repositorioProducto, servicioAlmacenamientoImagen);

        productoEntidad = new Producto(
                "P001",
                "Paracetamol",
                "Paracetamol genérico",
                "Tabletas 500mg",
                "500mg",
                "Comprimidos",
                10,
                100,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0001.jpg"
        );

        productosFiltrados = List.of(
                productoEntidad,
                new Producto("P002","Ibuprofeno","Ibuprofeno genérico","Tabletas 400mg","400mg","Comprimidos",5,50,true, CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,"producto/P0002.jpg"),
                new Producto("P003","Amoxicilina","Amoxicilina genérica","Caja 12 cápsulas","500mg","mg",20,200,false, CategoriaProducto.ANTIBIOTICOS,"producto/P0003.jpg")
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
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
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
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
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
                null,
                null
        );

        when(repositorioProducto.findById("P001")).thenReturn(Optional.of(productoEntidad));
        when(repositorioProducto.save(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Producto actualizado = servicioProducto.actualizar("P001", dto);

        assertEquals("Nuevo Nombre", actualizado.getNombreComercial());
        assertEquals(20, actualizado.getStockMinimo());
        assertFalse(actualizado.isActivo());

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
                null,
                null
        );

        when(repositorioProducto.findById("P999")).thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontradoException.class, () -> servicioProducto.actualizar("P999", dto));

        verify(repositorioProducto).findById("P999");
        verify(repositorioProducto, never()).save(any());
    }

    @Test
    @DisplayName("Buscar productos sin filtros devuelve todos los productos con campos correctos")
    void buscarProductosSinFiltros() {
        when(repositorioProducto.buscarPorCampos(null, null, null)).thenReturn(productosFiltrados);

        var resultado = servicioProducto.buscarPorCampos(null, null, null);

        assertEquals(productosFiltrados.size(), resultado.size());

        for (int i = 0; i < productosFiltrados.size(); i++) {
            var esperado = productosFiltrados.get(i);
            var actual = resultado.get(i);

            assertEquals(esperado.getIdProducto(), actual.getIdProducto());
            assertEquals(esperado.getNombreComercial(), actual.getNombreComercial());
            assertEquals(esperado.getNombreGenerico(), actual.getNombreGenerico());
            assertEquals(esperado.isActivo(), actual.isActivo());
        }
    }

    @Test
    @DisplayName("Buscar productos por nombre comercial devuelve coincidencias con campos correctos")
    void buscarProductosPorNombreComercial() {
        var productoEsperado = productosFiltrados.getFirst();
        when(repositorioProducto.buscarPorCampos("Paracetamol", null, null))
                .thenReturn(List.of(productoEsperado));

        var resultado = servicioProducto.buscarPorCampos("Paracetamol", null, null);

        assertEquals(1, resultado.size());
        var actual = resultado.getFirst();

        assertEquals(productoEsperado.getIdProducto(), actual.getIdProducto());
        assertEquals(productoEsperado.getNombreComercial(), actual.getNombreComercial());
        assertEquals(productoEsperado.getNombreGenerico(), actual.getNombreGenerico());
        assertEquals(productoEsperado.isActivo(), actual.isActivo());
    }

    @Test
    @DisplayName("Buscar productos por nombre genérico devuelve coincidencias con campos correctos")
    void buscarProductosPorNombreGenerico() {
        var productoEsperado = productosFiltrados.get(1);
        when(repositorioProducto.buscarPorCampos(null, "Ibuprofeno genérico", null))
                .thenReturn(List.of(productoEsperado));

        var resultado = servicioProducto.buscarPorCampos(null, "Ibuprofeno genérico", null);

        assertEquals(1, resultado.size());
        var actual = resultado.getFirst();

        assertEquals(productoEsperado.getIdProducto(), actual.getIdProducto());
        assertEquals(productoEsperado.getNombreComercial(), actual.getNombreComercial());
        assertEquals(productoEsperado.getNombreGenerico(), actual.getNombreGenerico());
        assertEquals(productoEsperado.isActivo(), actual.isActivo());
    }

    @Test
    @DisplayName("Buscar productos por activo devuelve coincidencias con campos correctos")
    void buscarProductosPorActivo() {
        var productosEsperados = productosFiltrados.stream()
                .filter(Producto::isActivo)
                .toList();

        when(repositorioProducto.buscarPorCampos(null, null, true))
                .thenReturn(productosEsperados);

        var resultado = servicioProducto.buscarPorCampos(null, null, true);

        assertEquals(productosEsperados.size(), resultado.size());

        for (int i = 0; i < productosEsperados.size(); i++) {
            var esperado = productosEsperados.get(i);
            var actual = resultado.get(i);

            assertEquals(esperado.getIdProducto(), actual.getIdProducto());
            assertEquals(esperado.getNombreComercial(), actual.getNombreComercial());
            assertEquals(esperado.getNombreGenerico(), actual.getNombreGenerico());
            assertEquals(esperado.isActivo(), actual.isActivo());
        }
    }

    @Test
    @DisplayName("Buscar productos sin coincidencias devuelve lista vacía")
    void buscarProductosSinCoincidencias() {
        when(repositorioProducto.buscarPorCampos("X", null, null))
                .thenReturn(List.of());

        var resultado = servicioProducto.buscarPorCampos("X", null, null);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("guardarFoto guarda la foto, actualiza el producto y persiste")
    void guardarFoto() {
        var foto = mock(MultipartFile.class);
        when(foto.isEmpty()).thenReturn(false);

        var producto = new Producto();
        producto.setIdProducto("P001");

        when(repositorioProducto.findById("P001"))
                .thenReturn(Optional.of(producto));

        when(servicioAlmacenamientoImagen.guardarFoto(foto, "producto", "P"))
                .thenReturn("producto/P0001.jpg");

        servicioProducto.guardarFoto("P001", foto);

        assertEquals("producto/P0001.jpg", producto.getUrlFoto());
        verify(servicioAlmacenamientoImagen).guardarFoto(foto, "producto", "P");
        verify(repositorioProducto).save(producto);
    }

    @Test
    @DisplayName("guardarFoto con foto nula o vacía no hace nada")
    void guardarFotoNulaOVacia() {
        servicioProducto.guardarFoto("P001", null);

        verifyNoInteractions(repositorioProducto);
        verifyNoInteractions(servicioAlmacenamientoImagen);

        var vacia = mock(MultipartFile.class);
        when(vacia.isEmpty()).thenReturn(true);

        servicioProducto.guardarFoto("P001", vacia);

        verifyNoInteractions(repositorioProducto);
        verifyNoInteractions(servicioAlmacenamientoImagen);
    }

    @Test
    @DisplayName("guardarFoto propaga ImagenAlmacenadaException si ocurre un error")
    void guardarFotoConErrorInterno() {
        var foto = mock(MultipartFile.class);
        when(foto.isEmpty()).thenReturn(false);

        var producto = new Producto();
        producto.setIdProducto("P001");

        when(repositorioProducto.findById("P001"))
                .thenReturn(Optional.of(producto));

        when(servicioAlmacenamientoImagen.guardarFoto(foto, "producto", "P"))
                .thenThrow(new ImagenAlmacenadaException());

        assertThrows(ImagenAlmacenadaException.class,
                () -> servicioProducto.guardarFoto("P001", foto));

        verify(repositorioProducto, never()).save(any());
    }
}
