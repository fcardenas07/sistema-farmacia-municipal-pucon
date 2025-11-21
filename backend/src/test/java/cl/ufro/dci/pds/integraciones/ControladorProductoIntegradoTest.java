/*
package cl.ufro.dci.pds.integraciones;

import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.RepositorioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.RepositorioProducto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.RepositorioLote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.RepositorioMovimiento;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.TipoMovimiento;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.RepositorioStock;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
@SpringBootTest
@AutoConfigureMockMvc
class ControladorProductoIntegradoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepositorioProducto repositorioProducto;

    @Autowired
    private RepositorioCodigo repositorioCodigo;

    @Autowired
    private RepositorioLote repositorioLote;

    @Autowired
    private RepositorioStock repositorioStock;

    @Autowired
    private RepositorioMovimiento repositorioMovimiento;

    @Autowired
    private ObjectMapper objectMapper;

    private List<String> idsProductos;
    private List<String> idsCodigos;

    @BeforeEach
    void setUp() throws IOException {
        repositorioMovimiento.deleteAll();
        repositorioStock.deleteAll();
        repositorioLote.deleteAll();
        repositorioCodigo.deleteAll();
        repositorioProducto.deleteAll();
        idsProductos = new ArrayList<>();
        idsCodigos = new ArrayList<>();

        var fotosDir = Paths.get("src/test/resources/assets-test/productos");
        if (Files.exists(fotosDir)) {
            Files.walk(fotosDir)
                    .filter(Files::isRegularFile)
                    .forEach(f -> {
                        try {
                            Files.delete(f);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }

        var producto1 = new Producto(
                "Paracetamol", "Paracetamol",
                "Tabletas", "500", "mg",
                10, 100, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                null
        );
        producto1 = repositorioProducto.save(producto1);
        idsProductos.add(producto1.getIdProducto());

        var codigo1 = new Codigo();
        codigo1.setCodigoBarra("1234567890123");
        codigo1.setTipoCodigo("EAN");
        codigo1.setActivo(true);
        codigo1.setProducto(producto1);
        codigo1 = repositorioCodigo.save(codigo1);
        idsCodigos.add(codigo1.getIdCodigo());

        var lote1 = new Lote();
        lote1.setCodigo(codigo1);
        lote1.setNumeroLote("NUM001");
        lote1.setFechaElaboracion(LocalDate.now().minusMonths(1));
        lote1.setFechaVencimiento(LocalDate.now().plusMonths(12));
        lote1.setEstado("DISPONIBLE");

        var stock1 = new Stock();
        stock1.setCantidadInicial(500);
        stock1.setCantidadActual(300);
        stock1.setLote(lote1);
        lote1.setStock(stock1);

        repositorioLote.save(lote1);

        var producto2 = new Producto(
                "Advil", "Ibuprofeno",
                "Tabletas", "400", "mg",
                5, 50, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0002.jpg"
        );
        producto2 = repositorioProducto.save(producto2);
        idsProductos.add(producto2.getIdProducto());

        var codigo2 = new Codigo();
        codigo2.setCodigoBarra("9876543210987");
        codigo2.setTipoCodigo("EAN");
        codigo2.setActivo(true);
        codigo2.setProducto(producto2);
        codigo2 = repositorioCodigo.save(codigo2);
        idsCodigos.add(codigo2.getIdCodigo());

        var lote2 = new Lote();
        lote2.setCodigo(codigo2);
        lote2.setNumeroLote("NUM002");
        lote2.setFechaElaboracion(LocalDate.now().minusMonths(2));
        lote2.setFechaVencimiento(LocalDate.now().plusMonths(10));
        lote2.setEstado("DISPONIBLE");

        var stock2 = new Stock();
        stock2.setCantidadInicial(500);
        stock2.setCantidadActual(100);
        stock2.setLote(lote2);
        lote2.setStock(stock2);

        repositorioLote.save(lote2);

        var producto3 = new Producto(
                "Amoxil", "Amoxicilina",
                "Caja 12 cápsulas", "500", "mg",
                20, 200, false,
                CategoriaProducto.ANTIBIOTICOS,
                "producto/P0003.jpg"
        );
        producto3 = repositorioProducto.save(producto3);
        idsProductos.add(producto3.getIdProducto());

        var codigo3 = new Codigo();
        codigo3.setCodigoBarra("1112223334445");
        codigo3.setTipoCodigo("EAN");
        codigo3.setActivo(true);
        codigo3.setProducto(producto3);
        codigo3 = repositorioCodigo.save(codigo3);
        idsCodigos.add(codigo3.getIdCodigo());

        var lote3 = new Lote();
        lote3.setCodigo(codigo3);
        lote3.setNumeroLote("NUM003");
        lote3.setFechaElaboracion(LocalDate.now().minusMonths(3));
        lote3.setFechaVencimiento(LocalDate.now().plusMonths(6));
        lote3.setEstado("NO_DISPONIBLE");

        var stock3 = new Stock();
        stock3.setCantidadInicial(200);
        stock3.setCantidadActual(20);
        stock3.setLote(lote3);
        lote3.setStock(stock3);

        repositorioLote.save(lote3);
    }

    @AfterEach
    void limpiarFotos() throws IOException {
        var fotosDir = Paths.get("src/test/resources/assets/productos");
        if (Files.exists(fotosDir)) {
            Files.walk(fotosDir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    @Test
    @DisplayName("Crear producto con un body vacío devuelve 400")
    void crearProductoConBodyNulo() throws Exception {
        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Crear producto con un body que no es un json devuelve 400")
    void crearProductoConBodyInvalidoFormato() throws Exception {
        String bodyInvalido = "esto no es JSON";
        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(bodyInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Crear producto con un json vacío devuelve 400")
    void crearProductoConBodyVacioJSON() throws Exception {
        ProductoACrear dtoVacio = new ProductoACrear(
                null, null, null, null, null,
                null, null, false, null, List.of()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dtoVacio)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede estar vacío"))
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con campos vacíos devuelve 400")
    void crearProductoConCamposVacios() throws Exception {
        ProductoACrear dtoCamposVacios = new ProductoACrear(
                "", "", null, null, null,
                null, null, false, null, List.of()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dtoCamposVacios)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede estar vacío"))
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con campos con tipo incorrecto devuelve 400")
    void crearProductoConCamposTipoIncorrecto() throws Exception {
        String bodyTipoIncorrecto = """
                {
                    "idProducto": "P001",
                    "nombreComercial": "Paracetamol",
                    "nombreGenerico": "Paracetamol",
                    "stockMinimo": "no es número"
                }
                """;

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(bodyTipoIncorrecto))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Crear producto válido devuelve 201 (integración)")
    void crearProductoValidoIntegracion() throws Exception {
        var dto = new ProductoACrear(
                "Diclofenaco 50mg",
                "Diclofenaco genérico",
                "Caja con 10 tabletas",
                "50mg",
                "mg",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("9990001112223", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProducto").isNotEmpty())
                .andExpect(jsonPath("$.nombreComercial").value("Diclofenaco 50mg"))
                .andExpect(jsonPath("$.activo").value(true))
                .andExpect(jsonPath("$.urlFoto").doesNotExist());
    }

    @Test
    @DisplayName("Actualizar producto válido devuelve 200")
    void actualizarProductoValido() throws Exception {
        var idProducto = idsProductos.getFirst();
        var idCodigo = idsCodigos.getFirst();

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
                List.of(new CodigoAModificar(idCodigo, "1234567890123", null, true))
        );

        System.out.println(idCodigo);

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreComercial").value("Nuevo Nombre"))
                .andExpect(jsonPath("$.activo").value(false))
                .andExpect(jsonPath("$.codigos[0].idCodigo").value(idCodigo))
                .andExpect(jsonPath("$.codigos[0].codigoBarra").value("1234567890123"));
    }

    @Test
    @DisplayName("Actualizar los códigos de un producto válido devuelve 200")
    void actualizarCodigosProductoValido() throws Exception {
        var producto = new Producto(
                "Producto Test",
                "Producto Test Genérico",
                "Presentación",
                "10mg",
                "mg",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                null
        );

        var productoGuardado = repositorioProducto.save(producto);
        var idProducto = productoGuardado.getIdProducto();

        var codigo1 = new Codigo();
        codigo1.setCodigoBarra("1111111111111");
        codigo1.setTipoCodigo("EAN");
        codigo1.setActivo(true);
        codigo1.setProducto(producto);
        codigo1 = repositorioCodigo.save(codigo1);
        var idCodigo1 = codigo1.getIdCodigo();

        var codigo2 = new Codigo();
        codigo2.setCodigoBarra("2222222222222");
        codigo2.setTipoCodigo("EAN");
        codigo2.setActivo(true);
        codigo2.setProducto(producto);
        codigo2 = repositorioCodigo.save(codigo2);
        var idCodigo2 = codigo2.getIdCodigo();

        var dto = new ProductoAModificar(
                "Nombre Modificado",
                null,
                null,
                null,
                null,
                10,
                50,
                false,
                null,
                List.of(
                        new CodigoAModificar(idCodigo1, "1111111111111", null, true),
                        new CodigoAModificar(idCodigo2, "2222222222222", null, true)
                )
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreComercial").value("Nombre Modificado"))
                .andExpect(jsonPath("$.activo").value(false))
                .andExpect(jsonPath("$.codigos[0].idCodigo").value(idCodigo1))
                .andExpect(jsonPath("$.codigos[0].codigoBarra").value("1111111111111"))
                .andExpect(jsonPath("$.codigos[1].idCodigo").value(idCodigo2))
                .andExpect(jsonPath("$.codigos[1].codigoBarra").value("2222222222222"));
    }

    @Test
    @DisplayName("Actualizar producto inexistente devuelve 404")
    void actualizarProductoNoExistente() throws Exception {
        String idProductoInexistente = "P999";

        var dto = new ProductoAModificar(
                "Paracetamol Modificado",
                "Paracetamol",
                "Tabletas",
                "500",
                "Comprimidos",
                10,
                100,
                true,
                null,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProductoInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Actualizar producto con código inexistente devuelve 404")
    void actualizarProductoConCodigoNoExistente() throws Exception {
        var producto = new Producto(
                "Producto Test",
                "Producto Test Genérico",
                "Presentación",
                "10mg",
                "mg",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                null
        );

        repositorioProducto.save(producto);

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                null,
                10,
                50,
                true,
                null,
                List.of(new CodigoAModificar("C999", "0000000000000", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", "P010")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Actualizar producto con código que no pertenece al producto devuelve 404")
    void actualizarProductoConCodigoNoPertenece() throws Exception {
        var producto1 = new Producto(
                "Prod 1",
                "Gen 1",
                "Pres",
                "10mg",
                "mg",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                null
        );

        var producto2 = new Producto(
                "Prod 2",
                "Gen 2",
                "Pres",
                "10mg",
                "mg",
                5,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                null
        );

        repositorioProducto.save(producto1);
        repositorioProducto.save(producto2);

        var codigo = new Codigo();
        codigo.setCodigoBarra("7800987654321");
        codigo.setTipoCodigo("EAN");
        codigo.setActivo(true);
        codigo.setProducto(producto2);
        repositorioCodigo.save(codigo);

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                null,
                10,
                50,
                true,
                null,
                List.of(new CodigoAModificar("C002", "7800987654321", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", "P011")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Actualizar producto con lista de códigos nula no modifica códigos y devuelve 200")
    void actualizarProductoConCodigosNulosNoModifica() throws Exception {
        var producto = new Producto(
                "Producto Test",
                "Genérico Test",
                "Presentación",
                "10mg",
                "mg",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                null
        );

        producto = repositorioProducto.save(producto);
        var productoId = producto.getIdProducto();

        var codigo = new Codigo();
        codigo.setCodigoBarra("130320101203");
        codigo.setTipoCodigo("EAN");
        codigo.setActivo(true);
        codigo.setProducto(producto);
        codigo = repositorioCodigo.save(codigo);
        var idCodigo = codigo.getIdCodigo();

        var dto = new ProductoAModificar(
                "Nombre Modificado",
                null,
                null,
                null,
                null,
                10,
                50,
                false,
                null,
                null
        );

        mockMvc.perform(patch("/productos/{id}", productoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreComercial").value("Nombre Modificado"))
                .andExpect(jsonPath("$.activo").value(false))
                .andExpect(jsonPath("$.codigos[0].idCodigo").value(idCodigo))
                .andExpect(jsonPath("$.codigos[0].codigoBarra").value("130320101203"));
    }

    @Test
    @DisplayName("Crear producto con nombre comercial vacío devuelve 400")
    void crearProductoConNombreComercialVacio() throws Exception {
        var dto = new ProductoACrear(
                "",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("1234567890123", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con nombre genérico vacío devuelve 400")
    void crearProductoConNombreGenericoVacio() throws Exception {
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIVIRALES,
                List.of(new CodigoACrear("1234567890124", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con stock máximo menor que stock mínimo devuelve 400")
    void crearProductoConStockMaxMenorQueStockMin() throws Exception {
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                50,
                10,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("1234567890126", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['stockValido']").value("El stock máximo debe ser mayor o igual al stock mínimo"));
    }

    @Test
    @DisplayName("Crear producto con nombre comercial muy largo devuelve 400")
    void crearProductoConNombreComercialMuyLargo() throws Exception {
        String nombreComercial = "a".repeat(201);
        var dto = new ProductoACrear(
                nombreComercial,
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("1234567890127", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede tener más de 200 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con nombre genérico muy largo devuelve 400")
    void crearProductoConNombreGenericoMuyLargo() throws Exception {
        String nombreGen = "a".repeat(201);
        var dto = new ProductoACrear(
                "Nombre Comercial",
                nombreGen,
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("1234567890129", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede tener más de 200 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con presentación muy larga devuelve 400")
    void crearProductoConPresentacionMuyLarga() throws Exception {
        String presentacion = "a".repeat(501);
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "Nombre Genérico",
                presentacion,
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("1234567890130", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.presentacion").value("La presentación no puede tener más de 500 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con dosificación muy larga devuelve 400")
    void crearProductoConDosificacionMuyLarga() throws Exception {
        String dosificacion = "a".repeat(101);
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                dosificacion,
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIFUNGICOS,
                List.of(new CodigoACrear("1234567890131", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.dosificacion").value("La dosificación no puede tener más de 100 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con unidad de medida muy larga devuelve 400")
    void crearProductoConUnidadMedidaMuyLarga() throws Exception {
        String unidad = "a".repeat(51);
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                unidad,
                10,
                50,
                true,
                CategoriaProducto.DERMATOLOGICOS,
                List.of(new CodigoACrear("1234567890132", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.unidadMedida").value("La unidad de medida no puede tener más de 50 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con stock máximo negativo y menor que stock mínimo devuelve 400")
    void crearProductoConStockMaximoNegativoYMenorQueMin() throws Exception {
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                -1,
                true,
                CategoriaProducto.ANTIFUNGICOS,
                List.of(new CodigoACrear("1234567890128", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['stockMaximo']").value("El stock máximo no puede ser negativo"))
                .andExpect(jsonPath("$.['stockValido']").value("El stock máximo debe ser mayor o igual al stock mínimo"));
    }

    @Test
    @DisplayName("Crear producto con stock mínimo negativo devuelve 400")
    void crearProductoConStockMinimoNegativo() throws Exception {
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                -1,
                50,
                true,
                CategoriaProducto.DERMATOLOGICOS,
                List.of(new CodigoACrear("1234567890125", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.stockMinimo").value("El stock mínimo no puede ser negativo"));
    }

    @Test
    @DisplayName("Crear producto con código de barra vacío devuelve 400")
    void crearProductoConCodigoBarraVacio() throws Exception {
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIHISTAMINICOS,
                List.of(new CodigoACrear("", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].codigoBarra']").value("El código de barra no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con código de barra demasiado largo devuelve 400")
    void crearProductoConCodigoBarraMuyLargo() throws Exception {
        String codigoLargo = "1".repeat(101);
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear(codigoLargo, "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].codigoBarra']")
                        .value("El código de barra no puede tener más de 100 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con tipo de código vacío devuelve 400")
    void crearProductoConTipoCodigoVacio() throws Exception {
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIVIRALES,
                List.of(new CodigoACrear("1234567890123", "", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].tipoCodigo']")
                        .value("El tipo de código no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con tipo de código demasiado largo devuelve 400")
    void crearProductoConTipoCodigoMuyLargo() throws Exception {
        String tipoLargo = "a".repeat(51);
        var dto = new ProductoACrear(
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("1234567890123", tipoLargo, true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].tipoCodigo']")
                        .value("El tipo de código no puede tener más de 50 caracteres"));
    }

    @Test
    @DisplayName("Actualizar producto con stock máximo menor que stock mínimo devuelve 400")
    void actualizarProductoStockMaxMenorQueStockMin() throws Exception {
        String idProducto = "P001";

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                null,
                50,
                10,
                true,
                CategoriaProducto.ANTIVIRALES,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['stockValido']")
                        .value("El stock máximo debe ser mayor o igual al stock mínimo"));
    }

    @Test
    @DisplayName("Actualizar producto con stock mínimo negativo devuelve 400")
    void actualizarProductoConStockMinimoNegativo() throws Exception {
        String idProducto = "P001";

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                null,
                -5,
                10,
                true,
                null,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.stockMinimo").value("El stock mínimo no puede ser negativo"));
    }

    @Test
    @DisplayName("Actualizar producto con stock máximo negativo devuelve 400")
    void actualizarProductoConStockMaximoNegativo() throws Exception {
        String idProducto = "P001";

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                null,
                10,
                -5,
                true,
                null,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.stockMaximo").value("El stock máximo no puede ser negativo"));
    }

    @Test
    @DisplayName("Actualizar producto con nombre comercial demasiado largo devuelve 400")
    void actualizarProductoNombreComercialMuyLargo() throws Exception {
        String idProducto = "P001";
        String nombreLargo = "a".repeat(201);

        var dto = new ProductoAModificar(
                nombreLargo,
                null,
                null,
                null,
                null,
                10,
                50,
                true,
                null,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede tener más de 200 caracteres"));
    }

    @Test
    @DisplayName("Actualizar producto con nombre genérico demasiado largo devuelve 400")
    void actualizarProductoNombreGenericoMuyLargo() throws Exception {
        String idProducto = "P001";
        String nombreLargo = "a".repeat(201);

        var dto = new ProductoAModificar(
                null,
                nombreLargo,
                null,
                null,
                null,
                10,
                50,
                true,
                null,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede tener más de 200 caracteres"));
    }

    @Test
    @DisplayName("Actualizar producto con presentación demasiado larga devuelve 400")
    void actualizarProductoPresentacionMuyLarga() throws Exception {
        String idProducto = "P001";
        String presentacionLarga = "a".repeat(501);

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                presentacionLarga,
                null,
                null,
                10,
                50,
                true,
                null,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.presentacion").value("La presentación no puede tener más de 500 caracteres"));
    }

    @Test
    @DisplayName("Actualizar producto con dosificación demasiado larga devuelve 400")
    void actualizarProductoDosificacionMuyLarga() throws Exception {
        String idProducto = "P001";
        String dosificacionLarga = "a".repeat(101);

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                dosificacionLarga,
                null,
                10,
                50,
                true,
                null,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.dosificacion").value("La dosificación no puede tener más de 100 caracteres"));
    }

    @Test
    @DisplayName("Actualizar producto con unidad de medida demasiado larga devuelve 400")
    void actualizarProductoUnidadMedidaMuyLarga() throws Exception {
        String idProducto = "P001";
        String unidadLarga = "a".repeat(51);

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                unidadLarga,
                10,
                50,
                true,
                null,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.unidadMedida").value("La unidad de medida no puede tener más de 50 caracteres"));
    }

    @Test
    @DisplayName("Actualizar producto con estado activo nulo devuelve 400")
    void actualizarProductoActivoNulo() throws Exception {
        String idProducto = "P001";

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                null,
                10,
                50,
                null,
                null,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.activo").value("El estado activo no puede ser nulo"));
    }

    @Test
    @DisplayName("Actualizar producto con código que no pertenece al producto devuelve 404")
    void actualizarProductoCodigoNoPertenece() throws Exception {
        String idProducto = "P001";

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                null,
                10,
                50,
                true,
                null,
                List.of(new CodigoAModificar("C999", "0000000000000", "EAN", true))
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Actualizar código con id vacío devuelve 400")
    void actualizarCodigoIdVacio() throws Exception {
        String idProducto = "P001";

        var dto = new CodigoAModificar("", "1234567890123", "EAN", true);

        var productoDto = new ProductoAModificar(
                null, null, null, null, null,
                10, 50, true, null,
                List.of(dto)
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(productoDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].idCodigo']").value("El id del código no puede estar vacío"));
    }

    @Test
    @DisplayName("Actualizar código con código de barra demasiado largo devuelve 400")
    void actualizarCodigoCodigoBarraMuyLargo() throws Exception {
        String idProducto = "P001";
        String codigoBarraLargo = "1".repeat(101);

        var dto = new CodigoAModificar("C001", codigoBarraLargo, "EAN", true);

        var productoDto = new ProductoAModificar(
                null, null, null, null, null,
                10, 50, true, null,
                List.of(dto)
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(productoDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].codigoBarra']").value("El código de barra no puede tener más de 100 caracteres"));
    }

    @Test
    @DisplayName("Actualizar código con tipo de código demasiado largo devuelve 400")
    void actualizarCodigoTipoCodigoMuyLargo() throws Exception {
        String idProducto = "P001";
        String tipoCodigoLargo = "a".repeat(51);

        var dto = new CodigoAModificar("C001", "1234567890123", tipoCodigoLargo, true);

        var productoDto = new ProductoAModificar(
                null, null, null, null, null,
                10, 50, true, null,
                List.of(dto)
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(productoDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].tipoCodigo']").value("El tipo de código no puede tener más de 50 caracteres"));
    }

    @Test
    @DisplayName("Actualizar códigos con IDs duplicados devuelve 400")
    void actualizarCodigosDuplicadosDevuelve400() throws Exception {
        String idProducto = "P001";

        var codigo1 = new CodigoAModificar("C001", null, null, null);
        var codigo2 = new CodigoAModificar("C002", null, null, null);
        var codigoDuplicado = new CodigoAModificar("C001", null, null, null);

        var productoDto = new ProductoAModificar(
                null, null, null, null, null,
                10, 50, true, null,
                List.of(codigo1, codigo2, codigoDuplicado)
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(productoDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigosUnicos").value("No se pueden repetir los IDs de los códigos"));
    }

    @Test
    @DisplayName("Crear producto con stock mínimo máximo permitido devuelve 400 si supera el límite")
    void crearProductoConStockMinimoExcedido() throws Exception {
        var dto = new ProductoACrear(
                "Producto Test",
                "Genérico Test",
                "Caja 10",
                "10mg",
                "mg",
                100_000_001,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("1234567890123", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.stockMinimo").value("El stock mínimo no puede superar 100.000.000"));
    }

    @Test
    @DisplayName("Crear producto con stock máximo excedido devuelve 400")
    void crearProductoConStockMaximoExcedido() throws Exception {
        var dto = new ProductoACrear(
                "Producto Test 2",
                "Genérico Test 2",
                "Caja 20",
                "20mg",
                "mg",
                10,
                100_000_001,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("1234567890456", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.stockMaximo").value("El stock máximo no puede superar 100.000.000"));
    }


    @Test
    @DisplayName("Actualizar producto con stock mínimo excedido devuelve 400")
    void actualizarProductoConStockMinimoExcedido() throws Exception {
        var dto = new ProductoAModificar(
                null,
                null,
                null,
                null,
                null,
                100_000_001,
                null,
                true,
                null,
                null
        );

        mockMvc.perform(patch("/productos/P001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.stockMinimo").value("El stock mínimo no puede superar 100.000.000"));
    }

    @Test
    @DisplayName("Actualizar producto con stock máximo excedido devuelve 400")
    void actualizarProductoConStockMaximoExcedido() throws Exception {
        var dto = new ProductoAModificar(
                null,
                null,
                null,
                null,
                null,
                null,
                100_000_001,
                true,
                null,
                null
        );

        mockMvc.perform(patch("/productos/P001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.stockMaximo").value("El stock máximo no puede superar 100.000.000"));
    }

    @Test
    @DisplayName("Crear producto con todos los campos en blanco devuelve 400")
    void crearProductoConTodosLosCamposEnBlanco() throws Exception {
        var codigo = new CodigoACrear("   ", "   ", true);

        ProductoACrear dto = new ProductoACrear(
                "   ",
                "   ",
                "   ",
                "   ",
                "   ",
                0,
                0,
                false,
                null,
                List.of(codigo)
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede estar vacío"))
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede estar vacío"))
                .andExpect(jsonPath("$.['codigos[0].codigoBarra']").value("El código de barra no puede estar vacío"))
                .andExpect(jsonPath("$.['codigos[0].tipoCodigo']").value("El tipo de código no puede estar vacío"));
    }

    @Test
    @DisplayName("Buscar productos sin filtros devuelve todos los productos")
    void buscarProductosSinFiltros() throws Exception {
        mockMvc.perform(get("/productos/buscar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(3))

                .andExpect(jsonPath("$.content[0].nombreComercial").value("Paracetamol"))
                .andExpect(jsonPath("$.content[0].stockTotal").value(300))
                .andExpect(jsonPath("$.content[0].disponible").value(true))

                .andExpect(jsonPath("$.content[1].nombreComercial").value("Advil"))
                .andExpect(jsonPath("$.content[1].stockTotal").value(100))
                .andExpect(jsonPath("$.content[1].disponible").value(true))

                .andExpect(jsonPath("$.content[2].nombreComercial").value("Amoxil"))
                .andExpect(jsonPath("$.content[2].stockTotal").value(20))
                .andExpect(jsonPath("$.content[2].disponible").value(false));
    }

    @Test
    @DisplayName("Buscar productos por nombre comercial devuelve coincidencias")
    void buscarProductosPorNombreComercial() throws Exception {
        mockMvc.perform(get("/productos/buscar")
                        .param("nombreComercial", "Advil"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].nombreComercial").value("Advil"))
                .andExpect(jsonPath("$.content[0].stockTotal").value(100))
                .andExpect(jsonPath("$.content[0].disponible").value(true));
    }

    @Test
    @DisplayName("Buscar productos por nombre genérico devuelve coincidencias")
    void buscarProductosPorNombreGenerico() throws Exception {
        mockMvc.perform(get("/productos/buscar")
                        .param("nombreGenerico", "Paracetamol"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].nombreGenerico").value("Paracetamol"))
                .andExpect(jsonPath("$.content[0].stockTotal").value(300))
                .andExpect(jsonPath("$.content[0].disponible").value(true));
    }

    @Test
    @DisplayName("Buscar productos por categoría devuelve coincidencias")
    void buscarProductosPorCategoria() throws Exception {
        mockMvc.perform(get("/productos/buscar")
                        .param("categoria", CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))

                .andExpect(jsonPath("$.content[0].categoria")
                        .value(CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS.getNombreLegible()))
                .andExpect(jsonPath("$.content[0].stockTotal").value(300))
                .andExpect(jsonPath("$.content[0].disponible").value(true))

                .andExpect(jsonPath("$.content[1].categoria")
                        .value(CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS.getNombreLegible()))
                .andExpect(jsonPath("$.content[1].stockTotal").value(100))
                .andExpect(jsonPath("$.content[1].disponible").value(true));
    }

    @Test
    @DisplayName("Buscar productos por activo devuelve coincidencias")
    void buscarProductosPorActivo() throws Exception {
        mockMvc.perform(get("/productos/buscar")
                        .param("activo", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))

                .andExpect(jsonPath("$.content[0].activo").value(true))
                .andExpect(jsonPath("$.content[0].stockTotal").value(300))
                .andExpect(jsonPath("$.content[0].disponible").value(true))

                .andExpect(jsonPath("$.content[1].activo").value(true))
                .andExpect(jsonPath("$.content[1].stockTotal").value(100))
                .andExpect(jsonPath("$.content[1].disponible").value(true));
    }

    @Test
    @DisplayName("Buscar productos sin coincidencias devuelve lista vacía")
    void buscarProductosSinCoincidencias() throws Exception {
        mockMvc.perform(get("/productos/buscar")
                        .param("nombreComercial", "NoExiste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    @DisplayName("Crear producto (JSON) y luego subir imagen funciona (200) [integración]")
    void crearProductoYLuegoSubirImagen() throws Exception {
        var dto = new ProductoACrear(
                "Prueba Producto", "Genérico Prueba",
                "Caja", "50mg", "mg",
                5, 100, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("0001112223334", "EAN", true))
        );

        var resultadoCreacion = mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProducto").exists())
                .andExpect(jsonPath("$.urlFoto").doesNotExist())
                .andReturn();

        var idProducto = JsonPath.read(
                resultadoCreacion.getResponse().getContentAsString(), "$.idProducto"
        ).toString();

        var foto = new MockMultipartFile(
                "foto",
                "imagen.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "contenido-de-prueba".getBytes()
        );

        mockMvc.perform(multipart("/productos/{id}/foto", idProducto)
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isOk());

        var producto = repositorioProducto.findById(idProducto).orElseThrow();
        assertThat(producto.getUrlFoto()).isNotNull();
    }

    @Test
    @DisplayName("Actualizar imagen del producto devuelve 200 (integración)")
    void actualizarImagenDelProductoIntegracion() throws Exception {
        String idProducto = idsProductos.getFirst();

        var foto = new MockMultipartFile(
                "foto",
                "ok.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "contenido-de-prueba".getBytes()
        );

        mockMvc.perform(multipart("/productos/{id}/foto", idProducto)
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isOk());

        var producto = repositorioProducto.findById(idProducto).orElseThrow();
        assertThat(producto.getUrlFoto()).isNotNull();
    }

    @Test
    @DisplayName("Actualizar imagen con formato no permitido devuelve 400 (integración)")
    void actualizarImagenFormatoNoPermitido() throws Exception {
        var foto = new MockMultipartFile(
                "foto",
                "archivo.txt",
                "text/plain",
                "hola".getBytes()
        );

        mockMvc.perform(multipart("/productos/{id}/foto", "P001")
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.foto")
                        .value("El archivo debe ser una imagen PNG o JPG"));
    }

    @Test
    @DisplayName("Actualizar imagen con nombre vacío devuelve 400 (integración)")
    void actualizarImagenNombreVacio() throws Exception {
        var foto = new MockMultipartFile(
                "foto",
                "   ",
                MediaType.IMAGE_JPEG_VALUE,
                "bytes".getBytes()
        );

        mockMvc.perform(multipart("/productos/{id}/foto", "P001")
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.foto")
                        .value("El archivo debe tener un nombre válido"));
    }

    @Test
    @DisplayName("Actualizar imagen muy grande devuelve 400 por validación")
    void actualizarImagenMuyGrande() throws Exception {
        var gigante = new byte[20_000_000];

        var foto = new MockMultipartFile(
                "foto",
                "ok.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                gigante
        );

        mockMvc.perform(multipart("/productos/{id}/foto", "P001")
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.foto").value("El archivo no puede superar los 10 MB"));
    }

    @Test
    @DisplayName("Actualizar imagen sin enviar archivo devuelve 400 (integración)")
    void actualizarImagenSinArchivo() throws Exception {
        mockMvc.perform(multipart("/productos/{id}/foto", "P001")
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.foto")
                        .value("El archivo no puede estar vacío"));
    }

    @Test
    @DisplayName("Actualizar imagen con archivo vacío devuelve 400 (integración)")
    void actualizarImagenArchivoVacio() throws Exception {
        var foto = new MockMultipartFile(
                "foto",
                "imagen.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                new byte[0]
        );

        mockMvc.perform(multipart("/productos/{id}/foto", "P001")
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.foto")
                        .value("El archivo no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con código de barras duplicado devuelve error de validación")
    void crearProductoCodigoDuplicado_validaCodigosUnicos() throws Exception {
        var dto = new ProductoACrear(
                "Paracetamol",
                "Paracetamol Genérico",
                "Tabletas 500mg",
                "500",
                "mg",
                10,
                100,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(
                        new CodigoACrear("1234567890123", "EAN", true),
                        new CodigoACrear("1234567890123", "EAN", true)
                )
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigosBarraUnicos").value("No puede haber códigos de barra duplicados"));
    }

    @Test
    @DisplayName("dar de baja un producto: actualiza estado, códigos, lotes, stocks y genera movimiento")
    void darBajaProductoIntegradoCompleto() throws Exception {
        var idProducto = idsProductos.getFirst();

        mockMvc.perform(
                patch("/productos/dar-de-baja/{id}", idProducto)
        ).andExpect(status().isOk());

        var producto = repositorioProducto.findById(idProducto).orElseThrow();
        assertFalse(producto.isActivo(), "El producto debe quedar inactivo");

        var codigos = repositorioCodigo.findAllByProducto_IdProducto(idProducto);
        assertTrue(codigos.stream().noneMatch(Codigo::isActivo), "Todos los códigos deben quedar inactivos");

        var idsCodigos = codigos.stream().map(Codigo::getIdCodigo).toList();
        var lotes = repositorioLote.findByCodigo_IdCodigoIn(idsCodigos);
        assertTrue(lotes.stream().allMatch(l -> "INACTIVO".equals(l.getEstado())),
                "Todos los lotes deben quedar marcados como INACTIVO");

        var stocks = lotes.stream().map(Lote::getStock).toList();
        assertTrue(stocks.stream().allMatch(s -> s.getCantidadActual() == 0),
                "Todos los stocks deben quedar en 0");

        var movimientos = repositorioMovimiento.findAll();
        assertFalse(movimientos.isEmpty(), "Debe generarse al menos un movimiento de baja");
        assertTrue(movimientos.stream().allMatch(m -> m.getTipoMovimiento().equals(TipoMovimiento.BAJA)),
                "Todos los movimientos deben ser tipo BAJA");
    }
}

 */