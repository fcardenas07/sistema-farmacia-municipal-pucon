package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.infraestructura.ImagenAlmacenadaException;
import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.*;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.*;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServicioAppProductoTest {

    private ServicioProducto servicioProducto;
    private ServicioCodigo servicioCodigo;
    private ServicioAppProducto servicioAppProducto;
    private ServicioLote servicioLote;

    private Producto productoEntidad;
    private Codigo codigoEntidad;

    private List<Producto> productosEntidadesFiltrados;
    private List<Lote> lotesSimulados;

    @BeforeEach
    void setUp() {
        servicioProducto = mock(ServicioProducto.class);
        servicioCodigo = mock(ServicioCodigo.class);
        servicioLote = mock(ServicioLote.class);
        servicioAppProducto = new ServicioAppProducto(servicioProducto, servicioCodigo, servicioLote);

        var productoParacetamol = new Producto(
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

        var productoIbuprofeno = new Producto(
                "P002",
                "Ibuprofeno",
                "Ibuprofeno genérico",
                "Tabletas 400mg",
                "400mg",
                "Comprimidos",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0002.jpg"
        );

        var productoAmoxicilina = new Producto(
                "P003",
                "Amoxicilina",
                "Amoxicilina genérica",
                "Caja 12 cápsulas",
                "500mg",
                "mg",
                20,
                200,
                false,
                CategoriaProducto.ANTIBIOTICOS,
                "producto/P0003.jpg"
        );

        productoEntidad = new Producto(
                productoParacetamol.getIdProducto(),
                productoParacetamol.getNombreComercial(),
                productoParacetamol.getNombreGenerico(),
                productoParacetamol.getPresentacion(),
                productoParacetamol.getDosificacion(),
                productoParacetamol.getUnidadMedida(),
                productoParacetamol.getStockMinimo(),
                productoParacetamol.getStockMaximo(),
                productoParacetamol.isActivo(),
                productoParacetamol.getCategoriaProducto(),
                null
        );

        codigoEntidad = new Codigo("C001","1234567890123","EAN",true, productoEntidad);

        productosEntidadesFiltrados = List.of(productoParacetamol, productoIbuprofeno, productoAmoxicilina);

        lotesSimulados = List.of(
                crearLoteConStock("L001", productoParacetamol, 100),
                crearLoteConStock("L002", productoIbuprofeno, 50),
                crearLoteConStock("L003", productoAmoxicilina, 200)
        );
    }

    private Lote crearLoteConStock(String idLote, Producto producto, int cantidadStock) {
        var lote = new Lote();
        lote.setIdLote(idLote);
        lote.setProducto(producto);

        var stock = new Stock();
        stock.setCantidadActual(cantidadStock);
        stock.setLote(lote);

        lote.setStock(stock);
        return lote;
    }

    @Test
    @DisplayName("Crear producto válido devuelve ProductoCreado")
    void crearProductoValido() {
        var dto = new ProductoACrear("P001", "Paracetamol", "Paracetamol genérico",
                "Tabletas 500mg", "500mg", "Comprimidos", 10, 100, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("C001", "1234567890123", "EAN", true)));

        when(servicioProducto.crear(any(ProductoACrear.class))).thenReturn(productoEntidad);
        when(servicioCodigo.crear(eq(productoEntidad), any(CodigoACrear.class))).thenReturn(codigoEntidad);
        when(servicioCodigo.obtenerCodigosConIdProducto("P001")).thenReturn(List.of(codigoEntidad));

        var creado = servicioAppProducto.crearProducto(dto);

        assertEquals("P001", creado.idProducto());
        assertEquals("Paracetamol", creado.nombreComercial());
        assertEquals(1, creado.codigos().size());
        assertEquals("C001", creado.codigos().getFirst().idCodigo());
        assertNull(creado.urlFoto());

        verify(servicioCodigo).crear(eq(productoEntidad), any(CodigoACrear.class));
    }

    @Test
    @DisplayName("Crear producto con código duplicado lanza CodigoDuplicadoException")
    void crearProductoCodigoDuplicado() {
        var dto = new ProductoACrear("P002", "Ibuprofeno", "Ibuprofeno genérico",
                "Tabletas 400mg", "400mg", "Comprimidos", 5, 50, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("C002", "9876543210987", "EAN", true)));

        when(servicioProducto.crear(any(ProductoACrear.class))).thenReturn(productoEntidad);
        when(servicioCodigo.crear(eq(productoEntidad), any(CodigoACrear.class)))
                .thenThrow(new CodigoDuplicadoException("C002"));

        assertThrows(CodigoDuplicadoException.class, () -> servicioAppProducto.crearProducto(dto));
    }

    @Test
    @DisplayName("Actualizar producto válido devuelve ProductoModificado")
    void actualizarProductoValido() {
        var id = "P001";

        var dto = new ProductoAModificar(
                "Nuevo Nombre",
                null,
                null,
                null,
                null,
                10,
                50,
                false,
                null,
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
        var id = "P001";

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                null,
                10,
                50,
                false,
                null,
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
        var id = "P999";
        var dto = new ProductoAModificar("Paracetamol Modificado", "Paracetamol genérico",
                "Tabletas 500mg", "500mg", "Comprimidos", 10, 100, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true)));

        when(servicioProducto.actualizar(eq(id), any(ProductoAModificar.class)))
                .thenThrow(new ProductoNoEncontradoException(id));

        assertThrows(ProductoNoEncontradoException.class, () -> servicioAppProducto.actualizarProducto(id, dto));
    }

    @Test
    @DisplayName("Actualizar producto con código inexistente lanza CodigoNoEncontradoException")
    void actualizarProductoConCodigoNoExistente() {
        var id = "P001";
        var dto = new ProductoAModificar("Nombre Nuevo", null, null, null, null,
                10, 50, true,
                null,
                List.of(new CodigoAModificar("C999", "0000000000000", "EAN", true)));

        when(servicioProducto.actualizar(eq(id), any(ProductoAModificar.class))).thenReturn(productoEntidad);
        when(servicioCodigo.actualizarParaProducto(eq(id), any(CodigoAModificar.class)))
                .thenThrow(new CodigoNoEncontradoException("C999"));

        assertThrows(CodigoNoEncontradoException.class, () -> servicioAppProducto.actualizarProducto(id, dto));
    }

    @Test
    @DisplayName("Actualizar producto con código que no pertenece al producto lanza CodigoNoPerteneceProductoException")
    void actualizarProductoConCodigoNoPertenece() {
        var id = "P001";
        var dto = new ProductoAModificar("Nombre Nuevo", null, null, null, null,
                10, 50, true, null,
                List.of(new CodigoAModificar("C002", "7800987654321", "EAN", true)));

        when(servicioProducto.actualizar(eq(id), any(ProductoAModificar.class))).thenReturn(productoEntidad);
        when(servicioCodigo.actualizarParaProducto(eq(id), any(CodigoAModificar.class)))
                .thenThrow(new CodigoNoPerteneceProductoException("C002", id));

        assertThrows(CodigoNoPerteneceProductoException.class, () -> servicioAppProducto.actualizarProducto(id, dto));
    }

    @Test
    @DisplayName("Actualizar producto con lista de códigos nula no modifica códigos y devuelve ProductoModificado")
    void actualizarProductoConCodigosNulosNoModifica() {
        var id = "P001";
        var dto = new ProductoAModificar("Nombre Nuevo", null, null, null, null,
                10, 50, true, null, null);

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

    @Test
    @DisplayName("Buscar todos los productos sin filtros devuelve todos los productos con stockTotal y disponible")
    void buscarTodosLosProductosSinFiltros() {
        when(servicioProducto.buscarPorCampos(null, null, null))
                .thenReturn(productosEntidadesFiltrados);

        when(servicioLote.obtenerLotesDeProductos(anyList()))
                .thenReturn(lotesSimulados);

        var resultado = servicioAppProducto.buscarProductosFiltrados(null, null, null);

        assertEquals(3, resultado.size());

        assertEquals("P001", resultado.getFirst().idProducto());
        assertEquals(100, resultado.get(0).stockTotal());
        assertTrue(resultado.get(0).isDisponible());

        assertEquals("P002", resultado.get(1).idProducto());
        assertEquals(50, resultado.get(1).stockTotal());
        assertTrue(resultado.get(1).isDisponible());

        assertEquals("P003", resultado.get(2).idProducto());
        assertEquals(200, resultado.get(2).stockTotal());
        assertFalse(resultado.get(2).isDisponible());
    }

    @Test
    @DisplayName("Buscar productos por nombre comercial devuelve coincidencias")
    void buscarProductosPorNombreComercialDevuelveCoincidencias() {
        when(servicioProducto.buscarPorCampos("Paracetamol", null, null))
                .thenReturn(List.of(productosEntidadesFiltrados.getFirst()));

        when(servicioLote.obtenerLotesDeProductos(anyList()))
                .thenReturn(lotesSimulados);

        var resultado = servicioAppProducto.buscarProductosFiltrados("Paracetamol", null, null);

        assertEquals(1, resultado.size());
        assertEquals("Paracetamol", resultado.getFirst().nombreComercial());
        assertEquals(100, resultado.getFirst().stockTotal());
        assertTrue(resultado.getFirst().isDisponible());
    }

    @Test
    @DisplayName("Buscar productos por nombre genérico devuelve coincidencias")
    void buscarProductosPorNombreGenericoDevuelveCoincidencias() {
        when(servicioProducto.buscarPorCampos(null, "Ibuprofeno genérico", null))
                .thenReturn(List.of(productosEntidadesFiltrados.get(1)));

        when(servicioLote.obtenerLotesDeProductos(anyList()))
                .thenReturn(lotesSimulados);

        var resultado = servicioAppProducto.buscarProductosFiltrados(null, "Ibuprofeno genérico", null);

        assertEquals(1, resultado.size());
        assertEquals("Ibuprofeno genérico", resultado.getFirst().nombreGenerico());
        assertEquals(50, resultado.getFirst().stockTotal());
        assertTrue(resultado.getFirst().isDisponible());
    }

    @Test
    @DisplayName("Buscar productos activos devuelve solo productos activos")
    void buscarProductosActivosDevuelveSoloActivos() {
        when(servicioProducto.buscarPorCampos(null, null, true))
                .thenReturn(productosEntidadesFiltrados.stream()
                        .filter(Producto::isActivo)
                        .toList());

        when(servicioLote.obtenerLotesDeProductos(anyList()))
                .thenReturn(lotesSimulados);

        var resultado = servicioAppProducto.buscarProductosFiltrados(null, null, true);

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(ProductoFiltrado::activo));

        assertEquals(100, resultado.get(0).stockTotal());
        assertEquals(50, resultado.get(1).stockTotal());
        assertTrue(resultado.get(0).isDisponible());
        assertTrue(resultado.get(1).isDisponible());
    }

    @Test
    @DisplayName("Buscar productos sin coincidencias devuelve lista vacía")
    void buscarProductosSinCoincidenciasDevuelveListaVacia() {
        when(servicioProducto.buscarPorCampos("X", null, null))
                .thenReturn(List.of());

        when(servicioLote.obtenerLotesDeProductos(anyList()))
                .thenReturn(List.of());

        var resultado = servicioAppProducto.buscarProductosFiltrados("X", null, null);

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Actualizar foto del producto llama a guardarFoto en servicioProducto")
    void actualizarFotoValida() {
        var foto = mock(MultipartFile.class);
        when(foto.getOriginalFilename()).thenReturn("nueva.jpg");

        doNothing().when(servicioProducto).guardarFoto("P001", foto);

        servicioAppProducto.actualizarFoto("P001", foto);

        verify(servicioProducto).guardarFoto("P001", foto);
    }

    @Test
    @DisplayName("Error al actualizar foto propaga ImagenAlmacenadaException")
    void actualizarFotoFalla() {

        var foto = mock(MultipartFile.class);
        when(foto.getOriginalFilename()).thenReturn("error.jpg");

        doThrow(new ImagenAlmacenadaException())
                .when(servicioProducto)
                .guardarFoto("P001", foto);

        assertThrows(
                ImagenAlmacenadaException.class,
                () -> servicioAppProducto.actualizarFoto("P001", foto)
        );
    }

    @Test
    @DisplayName("Obtener producto por ID devuelve ProductoBuscado con stockTotal correcto")
    void obtenerProductoPorId() {
        var codigos = List.of(new Codigo("C001", "123", "EAN", true, productoEntidad));
        when(servicioProducto.obtenerPorId("P001")).thenReturn(productoEntidad);
        when(servicioCodigo.obtenerCodigosConIdProducto("P001")).thenReturn(codigos);
        when(servicioLote.obtenerLotesDeProductos(List.of("P001"))).thenReturn(List.of(
                crearLoteConStock("L001", productoEntidad, 100)
        ));

        var resultado = servicioAppProducto.obtenerProductoPorId("P001");

        assertEquals("P001", resultado.idProducto());
        assertEquals(100, resultado.stockTotal());
        assertTrue(resultado.isDisponible());
        assertEquals(1, resultado.codigos().size());
    }

    @Test
    @DisplayName("Obtener producto por ID inexistente lanza ProductoNoEncontradoException")
    void obtenerProductoPorId_inexistente() {
        when(servicioProducto.obtenerPorId("P999"))
                .thenThrow(new ProductoNoEncontradoException("P999"));

        assertThrows(ProductoNoEncontradoException.class,
                () -> servicioAppProducto.obtenerProductoPorId("P999"));
    }
}
