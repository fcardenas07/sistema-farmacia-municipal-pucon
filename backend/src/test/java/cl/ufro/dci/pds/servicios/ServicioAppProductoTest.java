/*package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.infraestructura.ImagenAlmacenadaException;
import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.*;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.*;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.ServicioMovimiento;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.ServicioStock;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*
class ServicioAppProductoTest {

    private ServicioProducto servicioProducto;
    private ServicioCodigo servicioCodigo;
    private ServicioAppProducto servicioAppProducto;
    private ServicioLote servicioLote;
    private ServicioStock servicioStock;
    private ServicioMovimiento servicioMovimiento;

    private Producto productoEntidad;
    private Codigo codigoEntidad;

    private List<Producto> productosEntidadesFiltrados;
    private List<Lote> lotesSimulados;



    @BeforeEach
    void setUp() {
        servicioProducto = mock(ServicioProducto.class);
        servicioCodigo = mock(ServicioCodigo.class);
        servicioLote = mock(ServicioLote.class);
        servicioStock = mock(ServicioStock.class);
        servicioMovimiento = mock(ServicioMovimiento.class);
        servicioAppProducto = new ServicioAppProducto(servicioProducto, servicioCodigo, servicioLote, servicioStock, servicioMovimiento);

        var productoParacetamol = new Producto(
                "Paracetamol", "Paracetamol", "Tabletas", "500", "mg", 10, 100,
                true, CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS, null
        );

        var productoIbuprofeno = new Producto(
                "Advil", "Ibuprofeno", "Tabletas", "400", "mg", 5, 50,
                true, CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS, "producto/P0002.jpg"
        );

        var productoAmoxicilina = new Producto(
                "Amoxil", "Amoxicilina", "Caja 12 cápsulas", "500", "mg", 20, 200,
                false, CategoriaProducto.ANTIBIOTICOS, "producto/P0003.jpg"
        );

        productoEntidad = productoParacetamol;

        var codigoParacetamol = new Codigo("12222222222", "EAN", true, productoParacetamol);
        var codigoIbuprofeno = new Codigo("2222222222222221", "EAN", true, productoIbuprofeno);
        var codigoAmoxicilina = new Codigo("222222333333331", "EAN", false, productoAmoxicilina);

        codigoEntidad = codigoParacetamol;

        lotesSimulados = List.of(
                crearLoteConCodigo(codigoParacetamol, 100),
                crearLoteConCodigo(codigoIbuprofeno, 50),
                crearLoteConCodigo(codigoAmoxicilina, 200)
        );

        productosEntidadesFiltrados = List.of(productoParacetamol, productoIbuprofeno, productoAmoxicilina);

        ReflectionTestUtils.setField(productoParacetamol, "idProducto", "P001");
        ReflectionTestUtils.setField(productoIbuprofeno, "idProducto", "P002");
        ReflectionTestUtils.setField(productoAmoxicilina, "idProducto", "P003");

        ReflectionTestUtils.setField(codigoParacetamol, "idCodigo", "C001");
        ReflectionTestUtils.setField(codigoIbuprofeno, "idCodigo", "C002");
        ReflectionTestUtils.setField(codigoAmoxicilina, "idCodigo", "C003");
    }

    private Lote crearLoteConCodigo(Codigo codigo, int cantidadStock) {
        var lote = new Lote();
        lote.setCodigo(codigo);

        var stock = new Stock();
        stock.setCantidadActual(cantidadStock);
        stock.setLote(lote);

        lote.setStock(stock);
        return lote;
    }

    @Test
    @DisplayName("Crear producto válido devuelve ProductoCreado")
    void crearProductoValido() {
        var dto = new ProductoACrear("Paracetamol", "Paracetamol",
                "Tabletas", "500", "mg", 10, 100, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("1234567890123", "EAN", true)));

        when(servicioProducto.crear(any(ProductoACrear.class))).thenReturn(productoEntidad);
        when(servicioCodigo.crear(eq(productoEntidad), any(CodigoACrear.class))).thenReturn(codigoEntidad);
        when(servicioCodigo.obtenerCodigosConIdProducto("P001")).thenReturn(List.of(codigoEntidad));

        var creado = servicioAppProducto.crearProducto(dto);

        assertNotNull(creado.idProducto());
        assertEquals("Paracetamol", creado.nombreComercial());
        assertEquals(1, creado.codigos().size());
        assertEquals("C001", creado.codigos().getFirst().idCodigo());
        assertNull(creado.urlFoto());

        verify(servicioCodigo).crear(eq(productoEntidad), any(CodigoACrear.class));
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

        var modificado = servicioAppProducto.actualizarProducto(id, dto);

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

        var codigo2 = new Codigo("7800987654321", "EAN", true, productoEntidad);

        var codigos = List.of(
                codigoEntidad,
                codigo2
        );

        ReflectionTestUtils.setField(codigo2, "idCodigo", "C002");

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
        var productosPage = new PageImpl<>(productosEntidadesFiltrados);
        when(servicioProducto.buscarPorCampos(null, null, null, null, 0))
                .thenReturn(productosPage);

        var codigoP1 = mock(Codigo.class);
        var codigoP2 = mock(Codigo.class);
        var codigoP3 = mock(Codigo.class);

        when(codigoP1.getProducto()).thenReturn(productosEntidadesFiltrados.get(0));
        when(codigoP1.getIdCodigo()).thenReturn("C001");

        when(codigoP2.getProducto()).thenReturn(productosEntidadesFiltrados.get(1));
        when(codigoP2.getIdCodigo()).thenReturn("C002");

        when(codigoP3.getProducto()).thenReturn(productosEntidadesFiltrados.get(2));
        when(codigoP3.getIdCodigo()).thenReturn("C003");

        when(servicioCodigo.obtenerCodigosConIdProductoEn(anyList()))
                .thenReturn(List.of(codigoP1, codigoP2, codigoP3));

        var lote1 = mock(Lote.class);
        var lote2 = mock(Lote.class);
        var lote3 = mock(Lote.class);

        when(lote1.getCodigo()).thenReturn(codigoP1);
        when(lote2.getCodigo()).thenReturn(codigoP2);
        when(lote3.getCodigo()).thenReturn(codigoP3);

        var stock1 = mock(cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock.class);
        var stock2 = mock(cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock.class);
        var stock3 = mock(cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock.class);

        when(stock1.getCantidadActual()).thenReturn(100);
        when(stock2.getCantidadActual()).thenReturn(50);
        when(stock3.getCantidadActual()).thenReturn(200);

        when(lote1.getStock()).thenReturn(stock1);
        when(lote2.getStock()).thenReturn(stock2);
        when(lote3.getStock()).thenReturn(stock3);

        when(servicioLote.obtenerLotesDeCodigos(anyList()))
                .thenReturn(List.of(lote1, lote2, lote3));

        var resultado = servicioAppProducto.buscarProductosFiltrados(null, null, null, null, 0);

        assertEquals(3, resultado.getContent().size());

        var p1 = resultado.getContent().getFirst();
        assertEquals("P001", p1.idProducto());
        assertEquals(100, p1.stockTotal());
        assertTrue(p1.isDisponible());

        var p2 = resultado.getContent().get(1);
        assertEquals("P002", p2.idProducto());
        assertEquals(50, p2.stockTotal());
        assertTrue(p2.isDisponible());

        var p3 = resultado.getContent().get(2);
        assertEquals("P003", p3.idProducto());
        assertEquals(200, p3.stockTotal());
        assertFalse(p3.isDisponible());
    }

    @Test
    @DisplayName("Buscar productos por nombre comercial devuelve coincidencias")
    void buscarProductosPorNombreComercialDevuelveCoincidencias() {
        var pageMock = new PageImpl<>(List.of(productosEntidadesFiltrados.getFirst()));
        when(servicioProducto.buscarPorCampos("Paracetamol", null, null, null, 0))
                .thenReturn(pageMock);

        var codigo = mock(Codigo.class);
        when(codigo.getProducto()).thenReturn(productosEntidadesFiltrados.getFirst());
        when(codigo.getIdCodigo()).thenReturn("C001");

        when(servicioCodigo.obtenerCodigosConIdProductoEn(anyList()))
                .thenReturn(List.of(codigo));

        var lote = mock(Lote.class);
        when(lote.getCodigo()).thenReturn(codigo);

        var stock = mock(cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock.class);
        when(stock.getCantidadActual()).thenReturn(100);
        when(lote.getStock()).thenReturn(stock);

        when(servicioLote.obtenerLotesDeCodigos(anyList()))
                .thenReturn(List.of(lote));

        var resultado = servicioAppProducto.buscarProductosFiltrados("Paracetamol", null, null, null, 0);

        assertEquals(1, resultado.getContent().size());
        ProductoFiltrado p = resultado.getContent().getFirst();
        assertEquals("Paracetamol", p.nombreComercial());
        assertEquals(100, p.stockTotal());
        assertTrue(p.isDisponible());
    }

    @Test
    @DisplayName("Buscar productos por nombre genérico devuelve coincidencias")
    void buscarProductosPorNombreGenericoDevuelveCoincidencias() {
        var pageMockGen = new PageImpl<>(List.of(productosEntidadesFiltrados.get(1)));
        when(servicioProducto.buscarPorCampos(null, "Ibuprofeno", null, null, 0))
                .thenReturn(pageMockGen);

        var codigo = mock(Codigo.class);
        when(codigo.getProducto()).thenReturn(productosEntidadesFiltrados.get(1));
        when(codigo.getIdCodigo()).thenReturn("C002");

        when(servicioCodigo.obtenerCodigosConIdProductoEn(anyList()))
                .thenReturn(List.of(codigo));

        var lote = mock(Lote.class);
        when(lote.getCodigo()).thenReturn(codigo);

        var stock = mock(cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock.class);
        when(stock.getCantidadActual()).thenReturn(50);
        when(lote.getStock()).thenReturn(stock);

        when(servicioLote.obtenerLotesDeCodigos(anyList()))
                .thenReturn(List.of(lote));

        var resultadoGen = servicioAppProducto.buscarProductosFiltrados(null, "Ibuprofeno", null, null, 0);
        var pGen = resultadoGen.getContent().getFirst();

        assertEquals("Advil", pGen.nombreComercial());
        assertEquals("Ibuprofeno", pGen.nombreGenerico());
        assertEquals(50, pGen.stockTotal());
        assertTrue(pGen.isDisponible());
    }

    @Test
    @DisplayName("Buscar productos activos devuelve solo productos activos")
    void buscarProductosActivosDevuelveSoloActivos() {
        var pageActivos = new PageImpl<>(productosEntidadesFiltrados.stream()
                .filter(Producto::isActivo)
                .toList());

        when(servicioProducto.buscarPorCampos(null, null, true, null, 0))
                .thenReturn(pageActivos);

        List<Codigo> codigos = pageActivos.getContent().stream().map(p -> {
            var c = mock(Codigo.class);
            when(c.getProducto()).thenReturn(p);
            when(c.getIdCodigo()).thenReturn("C_" + p.getIdProducto());
            return c;
        }).toList();
        when(servicioCodigo.obtenerCodigosConIdProductoEn(anyList())).thenReturn(codigos);

        List<Lote> lotes = codigos.stream().map(c -> {
            var l = mock(Lote.class);
            when(l.getCodigo()).thenReturn(c);
            var s = mock(cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock.class);
            when(s.getCantidadActual()).thenReturn(100);
            when(l.getStock()).thenReturn(s);
            return l;
        }).toList();
        when(servicioLote.obtenerLotesDeCodigos(anyList())).thenReturn(lotes);

        var resultadoActivos = servicioAppProducto.buscarProductosFiltrados(null, null, true, null, 0);

        assertEquals(pageActivos.getContent().size(), resultadoActivos.getContent().size());
        assertTrue(resultadoActivos.getContent().stream().allMatch(ProductoFiltrado::activo));
    }

    @Test
    @DisplayName("Buscar productos por categoría devuelve coincidencias")
    void buscarProductosPorCategoriaDevuelveCoincidaciones() {
        var categoria = CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS;

        var filtrados = productosEntidadesFiltrados.stream()
                .filter(p -> p.getCategoriaProducto() == categoria)
                .toList();

        when(servicioProducto.buscarPorCampos(null, null, null, categoria, 0))
                .thenReturn(new PageImpl<>(filtrados));

        List<Codigo> codigos = filtrados.stream().map(p -> {
            var c = mock(Codigo.class);
            when(c.getProducto()).thenReturn(p);
            when(c.getIdCodigo()).thenReturn("C_" + p.getIdProducto());
            return c;
        }).toList();
        when(servicioCodigo.obtenerCodigosConIdProductoEn(anyList())).thenReturn(codigos);

        List<Lote> lotes = codigos.stream().map(c -> {
            var l = mock(Lote.class);
            when(l.getCodigo()).thenReturn(c);
            var s = mock(cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock.class);
            when(s.getCantidadActual()).thenReturn(100);
            when(l.getStock()).thenReturn(s);
            return l;
        }).toList();
        when(servicioLote.obtenerLotesDeCodigos(anyList())).thenReturn(lotes);

        var resultado = servicioAppProducto.buscarProductosFiltrados(null, null, null, categoria, 0);

        assertEquals(filtrados.size(), resultado.getContent().size());
        assertTrue(resultado.getContent().stream()
                .allMatch(p -> p.categoria().equals(categoria.getNombreLegible())));
    }

    @Test
    @DisplayName("Buscar productos sin coincidencias devuelve lista vacía")
    void buscarProductosSinCoincidenciasDevuelveListaVacia() {
        var pageVacia = new PageImpl<Producto>(List.of());
        when(servicioProducto.buscarPorCampos("X", null, null, null, 0))
                .thenReturn(pageVacia);

        when(servicioCodigo.obtenerCodigosConIdProductoEn(anyList())).thenReturn(List.of());
        when(servicioLote.obtenerLotesDeCodigos(anyList())).thenReturn(List.of());

        var resultadoVacio = servicioAppProducto.buscarProductosFiltrados("X", null, null, null, 0);
        assertTrue(resultadoVacio.getContent().isEmpty());
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
        when(servicioProducto.obtenerPorId("P001")).thenReturn(productoEntidad);

        var codigoEntidad = new Codigo("C001", "EAN", true, productoEntidad);
        when(servicioCodigo.obtenerCodigosConIdProducto("P001"))
                .thenReturn(List.of(codigoEntidad));

        var loteConStock = mock(Lote.class);
        when(loteConStock.getCodigo()).thenReturn(codigoEntidad);
        var stockMock = mock(Stock.class);
        when(stockMock.getCantidadActual()).thenReturn(100);
        when(loteConStock.getStock()).thenReturn(stockMock);

        when(servicioLote.obtenerLotesDeCodigos(anyList()))
                .thenReturn(List.of(loteConStock));

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

    @Test
    @DisplayName("Dar de baja un producto llama a todos los servicios correspondientes")
    void darBajaProducto() {
        String idProducto = "P001";

        var codigo1 = mock(Codigo.class);
        when(codigo1.getIdCodigo()).thenReturn("C001");
        when(codigo1.getProducto()).thenReturn(productoEntidad);

        var codigo2 = mock(Codigo.class);
        when(codigo2.getIdCodigo()).thenReturn("C002");
        when(codigo2.getProducto()).thenReturn(productoEntidad);

        when(servicioCodigo.obtenerCodigosConIdProducto(idProducto))
                .thenReturn(List.of(codigo1, codigo2));

        var lote1 = mock(Lote.class);
        when(lote1.getCodigo()).thenReturn(codigo1);
        var stock1 = mock(Stock.class);
        when(lote1.getStock()).thenReturn(stock1);
        when(stock1.getCantidadActual()).thenReturn(10);
        when(servicioStock.darBaja(stock1)).thenReturn(10);

        var lote2 = mock(Lote.class);
        when(lote2.getCodigo()).thenReturn(codigo2);
        var stock2 = mock(Stock.class);
        when(lote2.getStock()).thenReturn(stock2);
        when(stock2.getCantidadActual()).thenReturn(5);
        when(servicioStock.darBaja(stock2)).thenReturn(5);

        when(servicioLote.obtenerLotesDeCodigos(List.of("C001", "C002")))
                .thenReturn(List.of(lote1, lote2));

        servicioAppProducto.darBajaProducto(idProducto);

        verify(servicioProducto).darBaja(idProducto);
        verify(servicioCodigo).darBaja(codigo1);
        verify(servicioCodigo).darBaja(codigo2);

        verify(servicioStock).darBaja(stock1);
        verify(servicioStock).darBaja(stock2);

        verify(servicioLote).darBaja(lote1);
        verify(servicioLote).darBaja(lote2);

        verify(servicioMovimiento).registrarMovimientoPorBajaProducto(productoEntidad, lote1, 10);
        verify(servicioMovimiento).registrarMovimientoPorBajaProducto(productoEntidad, lote2, 5);
    }

}
*/
