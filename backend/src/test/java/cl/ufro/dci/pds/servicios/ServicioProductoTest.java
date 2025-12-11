package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.infraestructura.ImagenAlmacenadaException;
import cl.ufro.dci.pds.infraestructura.ServicioAlmacenamientoImagen;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.*;
import cl.ufro.dci.pds.fixtures.mothers.ProductoMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioProductoTest {

    private RepositorioProducto repositorioProducto;
    private ServicioAlmacenamientoImagen servicioAlmacenamientoImagen;
    private ServicioProducto servicioProducto;

    @BeforeEach
    void setUp() {
        repositorioProducto = mock(RepositorioProducto.class);
        servicioAlmacenamientoImagen = mock(ServicioAlmacenamientoImagen.class);
        servicioProducto = new ServicioProducto(repositorioProducto, servicioAlmacenamientoImagen);
    }

    @Test
    @DisplayName("validar y guardar guarda un producto si no existe duplicado")
    void validarYGuardarProductoValido() {
        var paracetamol = ProductoMother.paracetamol();

        when(repositorioProducto.existsByClaveUnica(
                eq(paracetamol.getNombreComercial()),
                eq(paracetamol.getNombreGenerico()),
                eq(paracetamol.getPresentacion()),
                eq(paracetamol.getDosificacion()),
                eq(paracetamol.getUnidadMedida()),
                eq(paracetamol.getFabricante().getIdFabricante())
        )).thenReturn(false);

        when(repositorioProducto.save(any(Producto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var resultado = servicioProducto.validarYGuardar(paracetamol);

        assertNotNull(resultado);
        assertEquals("P001", resultado.getIdProducto());

        verify(repositorioProducto).existsByClaveUnica(
                paracetamol.getNombreComercial(),
                paracetamol.getNombreGenerico(),
                paracetamol.getPresentacion(),
                paracetamol.getDosificacion(),
                paracetamol.getUnidadMedida(),
                paracetamol.getFabricante().getIdFabricante()
        );
        verify(repositorioProducto).save(paracetamol);
    }

    @Test
    @DisplayName("validar y guardar lanza excepción si el producto ya existe")
    void validarYGuardarProductoDuplicado() {
        var paracetamol = ProductoMother.paracetamol();

        when(repositorioProducto.existsByClaveUnica(
                eq(paracetamol.getNombreComercial()),
                eq(paracetamol.getNombreGenerico()),
                eq(paracetamol.getPresentacion()),
                eq(paracetamol.getDosificacion()),
                eq(paracetamol.getUnidadMedida()),
                eq(paracetamol.getFabricante().getIdFabricante())
        )).thenReturn(true);

        assertThrows(ProductoDuplicadoException.class,
                () -> servicioProducto.validarYGuardar(paracetamol));

        verify(repositorioProducto, never()).save(any());
    }

    @Test
    @DisplayName("validarYGuardar no lanza error si el fabricante es null y pasa null como idFabricante")
    void validarYGuardarConFabricanteNull() {
        var producto = ProductoMother.paracetamolSinId();
        producto.setFabricante(null);

        when(repositorioProducto.existsByClaveUnica(
                eq(producto.getNombreComercial()),
                eq(producto.getNombreGenerico()),
                eq(producto.getPresentacion()),
                eq(producto.getDosificacion()),
                eq(producto.getUnidadMedida()),
                isNull()
        )).thenReturn(false);

        when(repositorioProducto.save(any(Producto.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        var resultado = servicioProducto.validarYGuardar(producto);

        assertNotNull(resultado);
        verify(repositorioProducto).existsByClaveUnica(
                anyString(), anyString(), anyString(), anyInt(), anyString(), isNull()
        );
        verify(repositorioProducto).save(producto);
    }

    @Test
    @DisplayName("obtenerPorId devuelve el producto cuando existe")
    void obtenerPorIdExistente() {
        var paracetamol = ProductoMother.paracetamol();

        when(repositorioProducto.findById("P001"))
                .thenReturn(Optional.of(paracetamol));

        var resultado = servicioProducto.obtenerPorId("P001");

        assertNotNull(resultado);
        assertSame(paracetamol, resultado);
        assertEquals("P001", resultado.getIdProducto());
        assertEquals("Paracetamol", resultado.getNombreComercial());

        verify(repositorioProducto).findById("P001");
    }

    @Test
    @DisplayName("obtenerPorId lanza ProductoNoEncontradoException cuando no existe")
    void obtenerPorIdNoExistente() {
        when(repositorioProducto.findById("P999"))
                .thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontradoException.class,
                () -> servicioProducto.obtenerPorId("P999"));

        verify(repositorioProducto).findById("P999");
    }

    @Test
    @DisplayName("Actualizar producto válido modifica los campos enviados en el DTO y guarda el producto")
    void actualizarProductoValido() {
        var existente = ProductoMother.paracetamol();

        var dto = new ProductoAModificar(
                "Nuevo Nombre",
                null,
                null,
                null,
                null,
                20,
                200,
                null,
                null
        );

        when(repositorioProducto.findById("P001"))
                .thenReturn(Optional.of(existente));

        when(repositorioProducto.save(any(Producto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var actualizado = servicioProducto.actualizar("P001", dto);

        assertEquals("Nuevo Nombre", actualizado.getNombreComercial());
        assertEquals(20, actualizado.getStockMinimo());
        assertEquals(200, actualizado.getStockMaximo());
        assertTrue(actualizado.isActivo(), "Activo no debe cambiar si viene null en el DTO");

        verify(repositorioProducto).findById("P001");
        verify(repositorioProducto).save(existente);
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
                null,
                null
        );

        when(repositorioProducto.findById("P999"))
                .thenReturn(Optional.empty());

        var ex = assertThrows(ProductoNoEncontradoException.class,
                () -> servicioProducto.actualizar("P999", dto));

        verify(repositorioProducto).findById("P999");
        verify(repositorioProducto, never()).save(any());
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
    @DisplayName("guardarFoto lanza ProductoNoEncontradoException si el producto no existe")
    void guardarFotoProductoNoExistente() {
        var foto = mock(MultipartFile.class);
        when(foto.isEmpty()).thenReturn(false);

        when(repositorioProducto.findById("P999"))
                .thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontradoException.class,
                () -> servicioProducto.guardarFoto("P999", foto));

        verify(repositorioProducto).findById("P999");
        verify(servicioAlmacenamientoImagen, never()).guardarFoto(any(), anyString(), anyString());
        verify(repositorioProducto, never()).save(any());
    }

    @Test
    @DisplayName("dar de baja marca el producto como inactivo y lo guarda")
    void darBajaProducto() {
        var producto = ProductoMother.paracetamol();
        producto.setActivo(true);

        when(repositorioProducto.findById("P001"))
                .thenReturn(Optional.of(producto));
        when(repositorioProducto.save(any(Producto.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        servicioProducto.darBaja("P001");

        assertFalse(producto.isActivo());
        verify(repositorioProducto).findById("P001");
        verify(repositorioProducto).save(producto);
    }
}
