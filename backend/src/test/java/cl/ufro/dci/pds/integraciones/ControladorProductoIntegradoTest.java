package cl.ufro.dci.pds.integraciones;

import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.RepositorioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.RepositorioProducto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.RepositorioLote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() throws IOException {
            repositorioCodigo.deleteAll();
            repositorioLote.deleteAll();
            repositorioProducto.deleteAll();

            var fotosDir = Paths.get("src/test/resources/assets-test/productos");
            if (Files.exists(fotosDir)) {
                Files.walk(fotosDir)
                        .filter(Files::isRegularFile)
                        .forEach(f -> {
                            try { Files.delete(f); }
                            catch (IOException e) { e.printStackTrace(); }
                        });
            }

            var producto1 = new Producto(
                    "P001",
                    "Paracetamol 500mg",
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

            repositorioProducto.save(producto1);

            var codigo1 = new Codigo();
            codigo1.setIdCodigo("C001");
            codigo1.setCodigoBarra("1234567890123");
            codigo1.setTipoCodigo("EAN");
            codigo1.setActivo(true);
            codigo1.setProducto(producto1);
            repositorioCodigo.save(codigo1);

            var lote1 = new Lote();
            lote1.setIdLote("L001");
            lote1.setProducto(producto1);
            lote1.setNumeroLote("NUM001");
            lote1.setFechaElaboracion(LocalDate.now().minusMonths(1));
            lote1.setFechaVencimiento(LocalDate.now().plusMonths(12));
            lote1.setEstado("DISPONIBLE");

            var stock1 = new Stock();
            stock1.setIdStock("S001");
            stock1.setCantidadInicial(500);
            stock1.setCantidadActual(300);
            stock1.setLote(lote1);
            lote1.setStock(stock1);

            repositorioLote.save(lote1);

            var producto2 = new Producto(
                    "P002",
                    "Ibuprofeno 400mg",
                    "Ibuprofeno genérico",
                    "Caja con 10 tabletas",
                    "400mg",
                    "mg",
                    5,
                    50,
                    true,
                    CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                    null
            );

            repositorioProducto.save(producto2);

            var codigo2 = new Codigo();
            codigo2.setIdCodigo("C002");
            codigo2.setCodigoBarra("9876543210987");
            codigo2.setTipoCodigo("EAN");
            codigo2.setActivo(true);
            codigo2.setProducto(producto2);
            repositorioCodigo.save(codigo2);

            var lote2 = new Lote();
            lote2.setIdLote("L002");
            lote2.setProducto(producto2);
            lote2.setNumeroLote("NUM002");
            lote2.setFechaElaboracion(LocalDate.now().minusMonths(2));
            lote2.setFechaVencimiento(LocalDate.now().plusMonths(10));
            lote2.setEstado("DISPONIBLE");

            var stock2 = new Stock();
            stock2.setIdStock("S002");
            stock2.setCantidadInicial(500);
            stock2.setCantidadActual(100);
            stock2.setLote(lote2);
            lote2.setStock(stock2);

            repositorioLote.save(lote2);

            var producto3 = new Producto(
                    "P003",
                    "Amoxicilina 500mg",
                    "Amoxicilina genérica",
                    "Caja con 12 cápsulas",
                    "500mg",
                    "mg",
                    0,
                    0,
                    false,
                    CategoriaProducto.ANTIBIOTICOS,
                    null
            );

            repositorioProducto.save(producto3);

            var codigo3 = new Codigo();
            codigo3.setIdCodigo("C003");
            codigo3.setCodigoBarra("1112223334445");
            codigo3.setTipoCodigo("EAN");
            codigo3.setActivo(true);
            codigo3.setProducto(producto3);
            repositorioCodigo.save(codigo3);

            var lote3 = new Lote();
            lote3.setIdLote("L003");
            lote3.setProducto(producto3);
            lote3.setNumeroLote("NUM003");
            lote3.setFechaElaboracion(LocalDate.now().minusMonths(3));
            lote3.setFechaVencimiento(LocalDate.now().plusMonths(6));
            lote3.setEstado("NO_DISPONIBLE");

            var stock3 = new Stock();
            stock3.setIdStock("S003");
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
                null, null, null, null, null, null,
                null, null, false, null, List.of()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dtoVacio)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.idProducto").value("El id del producto no puede estar vacío"))
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede estar vacío"))
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede estar vacío"))
                .andExpect(jsonPath("$.codigos").value("El producto debe tener al menos un código"));
    }

    @Test
    @DisplayName("Crear producto con campos vacíos devuelve 400")
    void crearProductoConCamposVacios() throws Exception {
        ProductoACrear dtoCamposVacios = new ProductoACrear(
                "", "", "", null, null, null,
                null, null, false, null, List.of()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dtoCamposVacios)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.idProducto").value("El id del producto no puede estar vacío"))
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
                    "nombreGenerico": "Paracetamol genérico",
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
                "P004",
                "Diclofenaco 50mg",
                "Diclofenaco genérico",
                "Caja con 10 tabletas",
                "50mg",
                "mg",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("C004", "9990001112223", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProducto").value("P004"))
                .andExpect(jsonPath("$.nombreComercial").value("Diclofenaco 50mg"))
                .andExpect(jsonPath("$.activo").value(true))
                .andExpect(jsonPath("$.urlFoto").doesNotExist());
    }

    @Test
    @DisplayName("Crear producto con código duplicado devuelve 409")
    void crearProductoCodigoDuplicado() throws Exception {
        var productoExistente = new Producto(
                "P002",
                "Ibuprofeno 400mg",
                "Ibuprofeno genérico",
                "Tabletas 400mg",
                "400mg",
                "Comprimidos",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P001.jpg"
        );

        repositorioProducto.save(productoExistente);

        var codigoExistente = new Codigo();
        codigoExistente.setIdCodigo("C002");
        codigoExistente.setCodigoBarra("9876543210987");
        codigoExistente.setTipoCodigo("EAN");
        codigoExistente.setActivo(true);
        codigoExistente.setProducto(productoExistente);
        repositorioCodigo.save(codigoExistente);

        var dto = new ProductoACrear(
                "P003",
                "Diclofenaco 50mg",
                "Diclofenaco genérico",
                "Caja con 10 tabletas",
                "50mg",
                "mg",
                5,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("C002", "9876543210987", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Actualizar producto válido devuelve 200")
    void actualizarProductoValido() throws Exception {
        String idProducto = "P001";

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

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreComercial").value("Nuevo Nombre"))
                .andExpect(jsonPath("$.activo").value(false))
                .andExpect(jsonPath("$.codigos[0].idCodigo").value("C001"))
                .andExpect(jsonPath("$.codigos[0].codigoBarra").value("1234567890123"));
    }

    @Test
    @DisplayName("Actualizar los códigos de un producto válido devuelve 200")
    void actualizarCodigosProductoValido() throws Exception {
        var producto = new Producto(
                "P005",
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

        var codigo1 = new Codigo();
        codigo1.setIdCodigo("C101");
        codigo1.setCodigoBarra("1111111111111");
        codigo1.setTipoCodigo("EAN");
        codigo1.setActivo(true);
        codigo1.setProducto(producto);
        repositorioCodigo.save(codigo1);

        var codigo2 = new Codigo();
        codigo2.setIdCodigo("C102");
        codigo2.setCodigoBarra("2222222222222");
        codigo2.setTipoCodigo("EAN");
        codigo2.setActivo(true);
        codigo2.setProducto(producto);
        repositorioCodigo.save(codigo2);

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
                        new CodigoAModificar("C101", "1111111111111", null, true),
                        new CodigoAModificar("C102", "2222222222222", null, true)
                )
        );

        mockMvc.perform(patch("/productos/{id}", "P005")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreComercial").value("Nombre Modificado"))
                .andExpect(jsonPath("$.activo").value(false))
                .andExpect(jsonPath("$.codigos[0].idCodigo").value("C101"))
                .andExpect(jsonPath("$.codigos[0].codigoBarra").value("1111111111111"))
                .andExpect(jsonPath("$.codigos[1].idCodigo").value("C102"))
                .andExpect(jsonPath("$.codigos[1].codigoBarra").value("2222222222222"));
    }

    @Test
    @DisplayName("Actualizar producto inexistente devuelve 404")
    void actualizarProductoNoExistente() throws Exception {
        String idProductoInexistente = "P999";

        var dto = new ProductoAModificar(
                "Paracetamol Modificado",
                "Paracetamol genérico",
                "Tabletas 500mg",
                "500mg",
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
                "P010",
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
                "P011",
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
                "P012",
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
        codigo.setIdCodigo("C002");
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
                "P013",
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

        repositorioProducto.save(producto);

        var codigo = new Codigo();
        codigo.setIdCodigo("C001");
        codigo.setCodigoBarra("1234567890123");
        codigo.setTipoCodigo("EAN");
        codigo.setActivo(true);
        codigo.setProducto(producto);
        repositorioCodigo.save(codigo);

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

        mockMvc.perform(patch("/productos/{id}", "P013")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreComercial").value("Nombre Modificado"))
                .andExpect(jsonPath("$.activo").value(false))
                .andExpect(jsonPath("$.codigos[0].idCodigo").value("C001"))
                .andExpect(jsonPath("$.codigos[0].codigoBarra").value("1234567890123"));
    }

    @Test
    @DisplayName("Crear producto con id vacío devuelve 400")
    void crearProductoConIdVacio() throws Exception {
        ProductoACrear dto = new ProductoACrear(
                "",
                "Paracetamol",
                "Paracetamol genérico",
                "Tabletas 500mg",
                "500mg",
                "Comprimidos",
                10,
                100,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.idProducto").value("El id del producto no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con nombre comercial vacío devuelve 400")
    void crearProductoConNombreComercialVacio() throws Exception {
        var dto = new ProductoACrear(
                "P020",
                "",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("C010", "1234567890123", "EAN", true))
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
                "P021",
                "Nombre Comercial",
                "",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIVIRALES,
                List.of(new CodigoACrear("C011", "1234567890124", "EAN", true))
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
                "P023",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                50,
                10,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("C013", "1234567890126", "EAN", true))
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
                "P024",
                nombreComercial,
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("C014", "1234567890127", "EAN", true))
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
                "P026",
                "Nombre Comercial",
                nombreGen,
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("C016", "1234567890129", "EAN", true))
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
                "P027",
                "Nombre Comercial",
                "Nombre Genérico",
                presentacion,
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("C017", "1234567890130", "EAN", true))
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
                "P028",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                dosificacion,
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIFUNGICOS,
                List.of(new CodigoACrear("C018", "1234567890131", "EAN", true))
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
                "P029",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                unidad,
                10,
                50,
                true,
                CategoriaProducto.DERMATOLOGICOS,
                List.of(new CodigoACrear("C019", "1234567890132", "EAN", true))
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
                "P025",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                -1,
                true,
                CategoriaProducto.ANTIFUNGICOS,
                List.of(new CodigoACrear("C015", "1234567890128", "EAN", true))
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
                "P022",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                -1,
                50,
                true,
                CategoriaProducto.DERMATOLOGICOS,
                List.of(new CodigoACrear("C012", "1234567890125", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.stockMinimo").value("El stock mínimo no puede ser negativo"));
    }

    @Test
    @DisplayName("Crear producto sin códigos devuelve 400")
    void crearProductoSinCodigos() throws Exception {
        var dto = new ProductoACrear(
                "P050",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.DERMATOLOGICOS,
                List.of()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Crear producto con id código vacío devuelve 400")
    void crearProductoConIdCodigoVacio() throws Exception {
        var dto = new ProductoACrear(
                "P051",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.DERMATOLOGICOS,
                List.of(new CodigoACrear("", "1234567890123", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].idCodigo']").value("El id del código no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con código de barra vacío devuelve 400")
    void crearProductoConCodigoBarraVacio() throws Exception {
        var dto = new ProductoACrear(
                "P052",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIHISTAMINICOS,
                List.of(new CodigoACrear("C100", "", "EAN", true))
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
                "P053",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("C101", codigoLargo, "EAN", true))
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
                "P054",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIVIRALES,
                List.of(new CodigoACrear("C102", "1234567890123", "", true))
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
                "P055",
                "Nombre Comercial",
                "Nombre Genérico",
                "Caja 10",
                "10mg",
                "mg",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("C103", "1234567890123", tipoLargo, true))
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
    @DisplayName("Crear producto con códigos duplicados devuelve 400")
    void crearProductoConCodigosDuplicadosDevuelve400() throws Exception {
        var codigo1 = new CodigoACrear("C001", "123", "TipoA", true);
        var codigo2 = new CodigoACrear("C002", "456", "TipoB", true);
        var codigoDuplicado = new CodigoACrear("C001", "789", "TipoC", true);

        var productoDto = new ProductoACrear(
                "P001",
                "Producto A",
                "Genérico A",
                "Presentación",
                "Dosificación",
                "Unidad",
                10,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(codigo1, codigo2, codigoDuplicado)
        );

        mockMvc.perform(post("/productos")
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
                "P999",
                "Producto Test",
                "Genérico Test",
                "Caja 10",
                "10mg",
                "mg",
                100_000_001,
                50,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("C999", "1234567890123", "EAN", true))
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
                "P998",
                "Producto Test 2",
                "Genérico Test 2",
                "Caja 20",
                "20mg",
                "mg",
                10,
                100_000_001,
                true,
                CategoriaProducto.ANTIBIOTICOS,
                List.of(new CodigoACrear("C998", "1234567890456", "EAN", true))
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
        var codigo = new CodigoACrear("   ", "   ", "   ", true);

        ProductoACrear dto = new ProductoACrear(
                "   ",
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
                .andExpect(jsonPath("$.idProducto").value("El id del producto no puede estar vacío"))
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
                .andExpect(jsonPath("$.length()").value(3))

                .andExpect(jsonPath("$[0].idProducto").value("P001"))
                .andExpect(jsonPath("$[0].stockTotal").value(300))
                .andExpect(jsonPath("$[0].disponible").value(true))

                .andExpect(jsonPath("$[1].idProducto").value("P002"))
                .andExpect(jsonPath("$[1].stockTotal").value(100))
                .andExpect(jsonPath("$[1].disponible").value(true))

                .andExpect(jsonPath("$[2].idProducto").value("P003"))
                .andExpect(jsonPath("$[2].stockTotal").value(20))
                .andExpect(jsonPath("$[2].disponible").value(false));
    }

    @Test
    @DisplayName("Buscar productos por nombre comercial devuelve coincidencias")
    void buscarProductosPorNombreComercial() throws Exception {
        mockMvc.perform(get("/productos/buscar")
                        .param("nombreComercial", "Ibuprofeno 400mg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombreComercial").value("Ibuprofeno 400mg"))
                .andExpect(jsonPath("$[0].stockTotal").value(100))
                .andExpect(jsonPath("$[0].disponible").value(true));
    }

    @Test
    @DisplayName("Buscar productos por nombre genérico devuelve coincidencias")
    void buscarProductosPorNombreGenerico() throws Exception {
        mockMvc.perform(get("/productos/buscar")
                        .param("nombreGenerico", "Paracetamol genérico"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nombreGenerico").value("Paracetamol genérico"))
                .andExpect(jsonPath("$[0].stockTotal").value(300))
                .andExpect(jsonPath("$[0].disponible").value(true));
    }

    @Test
    @DisplayName("Buscar productos por activo devuelve coincidencias")
    void buscarProductosPorActivo() throws Exception {
        mockMvc.perform(get("/productos/buscar")
                        .param("activo", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))

                .andExpect(jsonPath("$[0].activo").value(true))
                .andExpect(jsonPath("$[0].stockTotal").value(300))
                .andExpect(jsonPath("$[0].disponible").value(true))

                .andExpect(jsonPath("$[1].activo").value(true))
                .andExpect(jsonPath("$[1].stockTotal").value(100))
                .andExpect(jsonPath("$[1].disponible").value(true));
    }

    @Test
    @DisplayName("Buscar productos sin coincidencias devuelve lista vacía")
    void buscarProductosSinCoincidencias() throws Exception {
        mockMvc.perform(get("/productos/buscar")
                        .param("nombreComercial", "NoExiste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("Crear producto (JSON) y luego subir imagen funciona (200) [integración]")
    void crearProductoYLuegoSubirImagen() throws Exception {
        var dto = new ProductoACrear(
                "P020", "Prueba Producto", "Genérico Prueba",
                "Caja", "50mg", "mg",
                5, 100, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("C020", "0001112223334", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProducto").value("P020"))
                .andExpect(jsonPath("$.urlFoto").doesNotExist());

        var foto = new MockMultipartFile(
                "foto",
                "imagen.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "contenido-de-prueba".getBytes()
        );

        mockMvc.perform(multipart("/productos/{id}/foto", "P020")
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isOk());

        var producto = repositorioProducto.findById("P020").orElseThrow();
        assertThat(producto.getUrlFoto()).isNotNull();
    }

    @Test
    @DisplayName("Actualizar imagen del producto devuelve 200 (integración)")
    void actualizarImagenDelProducto() throws Exception {
        var foto = new MockMultipartFile(
                "foto",
                "ok.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "bytes".getBytes()
        );

        mockMvc.perform(multipart("/productos/{id}/foto", "P001")
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isOk());

        var producto = repositorioProducto.findById("P001").orElseThrow();
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
}
