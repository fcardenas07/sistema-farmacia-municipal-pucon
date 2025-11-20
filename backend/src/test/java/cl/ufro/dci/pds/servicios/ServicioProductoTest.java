package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.infraestructura.ImagenAlmacenadaException;
import cl.ufro.dci.pds.infraestructura.ServicioAlmacenamientoImagen;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.ServicioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.*;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
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
                "Paracetamol",
                "Paracetamol",
                "Tabletas",
                "500",
                "mg",
                10,
                100,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0001.jpg"
        );
        ReflectionTestUtils.setField(productoEntidad, "idProducto", "P001");

        var producto2 = new Producto(
                "Advil",
                "Ibuprofeno",
                "Tabletas",
                "400",
                "mg",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0002.jpg"
        );
        ReflectionTestUtils.setField(producto2, "idProducto", "P002");

        var producto3 = new Producto(
                "Amoxil",
                "Amoxicilina",
                "Caja 12 cápsulas",
                "500",
                "mg",
                20,
                200,
                false,
                CategoriaProducto.ANTIBIOTICOS,
                "producto/P0003.jpg"
        );
        ReflectionTestUtils.setField(producto3, "idProducto", "P003");

        productosFiltrados = List.of(productoEntidad, producto2, producto3);
    }


    @Test
    @DisplayName("Crear producto válido guarda y devuelve el producto")
    void crearProductoValido() {
        var dto = new ProductoACrear(
                "Paracetamol",
                "Paracetamol",
                "Tabletas",
                "500",
                "mg",
                10,
                100,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                null
        );

        when(repositorioProducto.save(any(Producto.class)))
                .thenAnswer(invocation -> {
                    Producto p = invocation.getArgument(0);
                    ReflectionTestUtils.setField(p, "idProducto", "P001");
                    return p;
                });

        var creado = servicioProducto.crear(dto);

        assertNotNull(creado.getIdProducto());
        assertEquals("Paracetamol", creado.getNombreComercial());
        assertEquals("Paracetamol", creado.getNombreGenerico());
        assertEquals("Tabletas", creado.getPresentacion());
        assertEquals("500", creado.getDosificacion());
        assertEquals("mg", creado.getUnidadMedida());

        verify(repositorioProducto).save(any(Producto.class));
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
        var productosPage = new PageImpl<>(productosFiltrados);
        when(repositorioProducto.buscarPorCampos(
                eq(null),
                eq(null),
                eq(null),
                eq(null),
                any(Pageable.class)
        )).thenReturn(productosPage);

        var resultado = servicioProducto.buscarPorCampos(null, null, null, null, 0);

        assertEquals(productosPage.getContent().size(), resultado.getContent().size());

        for (var i = 0; i < productosPage.getContent().size(); i++) {
            var esperado = productosPage.getContent().get(i);
            var actual = resultado.getContent().get(i);
            assertNotNull(actual.getIdProducto());
            assertEquals(esperado.getNombreComercial(), actual.getNombreComercial());
            assertEquals(esperado.getNombreGenerico(), actual.getNombreGenerico());
            assertEquals(esperado.isActivo(), actual.isActivo());
        }
    }

    @Test
    @DisplayName("Buscar productos por nombre comercial devuelve coincidencias")
    void buscarProductosPorNombreComercial() {
        var pageMock = new PageImpl<>(List.of(productosFiltrados.getFirst()));
        when(repositorioProducto.buscarPorCampos(
                eq("Paracetamol"),
                eq(null),
                eq(null),
                eq(null),
                any(Pageable.class)
        )).thenReturn(pageMock);

        var resultado = servicioProducto.buscarPorCampos("Paracetamol", null, null, null, 0);

        assertEquals(1, resultado.getContent().size());
        var actual = resultado.getContent().getFirst();

        assertEquals("Paracetamol", actual.getNombreComercial());
        assertEquals("Paracetamol", actual.getNombreGenerico());
        assertTrue(actual.isActivo());
    }

    @Test
    @DisplayName("Buscar productos por nombre genérico devuelve coincidencias")
    void buscarProductosPorNombreGenerico() {
        var pageMockGen = new PageImpl<>(List.of(productosFiltrados.get(1)));
        when(repositorioProducto.buscarPorCampos(
                eq(null),
                eq("Ibuprofeno"),
                eq(null),
                eq(null),
                any(Pageable.class)
        )).thenReturn(pageMockGen);

        var resultado = servicioProducto.buscarPorCampos(null, "Ibuprofeno", null, null, 0);
        var actual = resultado.getContent().getFirst();

        assertNotNull(actual.getIdProducto());
        assertEquals("Advil", actual.getNombreComercial());
        assertEquals("Ibuprofeno", actual.getNombreGenerico());
        assertTrue(actual.isActivo());
    }

    @Test
    @DisplayName("Buscar productos por categoría devuelve coincidencias")
    void buscarProductosPorCategoria() {
        var categoria = CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS;
        var productosCategoria = productosFiltrados.stream()
                .filter(p -> p.getCategoriaProducto() == categoria)
                .toList();

        var pageMockCategoria = new PageImpl<>(productosCategoria);

        when(repositorioProducto.buscarPorCampos(
                eq(null),
                eq(null),
                eq(null),
                eq(categoria),
                any(Pageable.class)
        )).thenReturn(pageMockCategoria);

        var resultado = servicioProducto.buscarPorCampos(null, null, null, categoria, 0);

        assertEquals(productosCategoria.size(), resultado.getContent().size());

        for (var producto : resultado.getContent()) {
            assertNotNull(producto.getIdProducto());
            assertEquals(categoria, producto.getCategoriaProducto());
        }
    }

    @Test
    @DisplayName("Buscar productos activos devuelve solo productos activos")
    void buscarProductosPorActivo() {
        var pageActivos = new PageImpl<>(productosFiltrados.stream()
                .filter(Producto::isActivo)
                .toList());

        when(repositorioProducto.buscarPorCampos(
                eq(null),
                eq(null),
                eq(true),
                eq(null),
                any(Pageable.class)
        )).thenReturn(pageActivos);

        var resultado = servicioProducto.buscarPorCampos(null, null, true, null, 0);

        assertEquals(pageActivos.getContent().size(), resultado.getContent().size());
        assertTrue(resultado.getContent().stream().allMatch(Producto::isActivo));
    }

    @Test
    @DisplayName("Buscar productos sin coincidencias devuelve lista vacía")
    void buscarProductosSinCoincidencias() {
        var pageVacia = new PageImpl<Producto>(List.of());
        when(repositorioProducto.buscarPorCampos(
                eq("X"),
                eq(null),
                eq(null),
                eq(null),
                any(Pageable.class)
        )).thenReturn(pageVacia);

        var resultado = servicioProducto.buscarPorCampos("X", null, null, null, 0);

        assertTrue(resultado.getContent().isEmpty());
    }

    @Test
    @DisplayName("guardarFoto guarda la foto, actualiza el producto y persiste")
    void guardarFoto() {
        var foto = mock(MultipartFile.class);
        when(foto.isEmpty()).thenReturn(false);

        var producto = new Producto();

        when(repositorioProducto.findById("P001"))
                .thenReturn(Optional.of(producto));

        when(servicioAlmacenamientoImagen.guardarFoto(foto, "productos", "P"))
                .thenReturn("productos/P0001.jpg");

        servicioProducto.guardarFoto("P001", foto);

        assertEquals("productos/P0001.jpg", producto.getUrlFoto());
        verify(servicioAlmacenamientoImagen).guardarFoto(foto, "productos", "P");
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

        when(repositorioProducto.findById("P001"))
                .thenReturn(Optional.of(producto));

        when(servicioAlmacenamientoImagen.guardarFoto(foto, "productos", "P"))
                .thenThrow(new ImagenAlmacenadaException());

        assertThrows(ImagenAlmacenadaException.class,
                () -> servicioProducto.guardarFoto("P001", foto));

        verify(repositorioProducto, never()).save(any());
    }

    @Test
    @DisplayName("obtenerPorId devuelve el producto si existe")
    void obtenerPorIdExistente() {
        when(repositorioProducto.findById("P001")).thenReturn(Optional.of(productoEntidad));

        var resultado = servicioProducto.obtenerPorId("P001");

        assertNotNull(resultado);
        assertEquals("P001", resultado.getIdProducto());
        assertEquals("Paracetamol", resultado.getNombreComercial());

        verify(repositorioProducto).findById("P001");
    }

    @Test
    @DisplayName("obtenerPorId lanza ProductoNoEncontradoException si no existe")
    void obtenerPorIdNoExistente() {
        when(repositorioProducto.findById("P999")).thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontradoException.class, () -> servicioProducto.obtenerPorId("P999"));

        verify(repositorioProducto).findById("P999");
    }

    @Test
    @DisplayName("dar de baja marca el producto como inactivo y lo guarda")
    void darBajaProducto() {
        productoEntidad.setActivo(true);

        when(repositorioProducto.findById("P001")).thenReturn(Optional.of(productoEntidad));
        when(repositorioProducto.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        servicioProducto.darBaja("P001");

        assertFalse(productoEntidad.isActivo(), "El producto debe quedar inactivo");
        verify(repositorioProducto).findById("P001");
        verify(repositorioProducto).save(productoEntidad);
    }
}
