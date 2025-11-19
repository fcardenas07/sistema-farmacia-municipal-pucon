package cl.ufro.dci.pds.controladores;

import cl.ufro.dci.pds.infraestructura.ImagenAlmacenadaException;
import cl.ufro.dci.pds.infraestructura.SecurityConfig;
import cl.ufro.dci.pds.inventario.app.controladores.ControladorProducto;
import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoDuplicadoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoNoEncontradoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoNoPerteneceProductoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ProductoNoEncontradoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.RepositorioProducto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ControladorProducto.class)
@Import(SecurityConfig.class)
public class ControladorProductoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicioAppProducto servicioAppProducto;

    @MockitoBean
    private RepositorioProducto repositorioProducto;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductoACrear productoValido;

    private List<ProductoFiltrado> productosFiltrados;

    @BeforeEach
    void setUp() {
        repositorioProducto.deleteAll();
        productoValido = new ProductoACrear(
                "Paracetamol",
                "Paracetamol",
                "Tabletas",
                "500",
                "mg",
                10,
                100,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(
                        new CodigoACrear("C001", "1234567890123", "EAN", true),
                        new CodigoACrear("C002", "7800987654321", "EAN13", true)
                )
        );

        productosFiltrados = List.of(
                new ProductoFiltrado("P001", "Paracetamol", "Paracetamol",
                        CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS.getNombreLegible(),
                        true, 300, "productos/P001.jpg"),
                new ProductoFiltrado("P002", "Advil", "Ibuprofeno",
                        CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS.getNombreLegible(),
                        true, 100, "productos/P002.jpg"),
                new ProductoFiltrado("P003", "Amoxil", "Amoxicilina",
                        CategoriaProducto.ANTIBIOTICOS.getNombreLegible(),
                        false, 20, "productos/P003.jpg")
        );
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
        var bodyInvalido = "esto no es JSON";
        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(bodyInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Crear producto con un json vacío devuelve 400")
    void crearProductoConBodyVacioJSON() throws Exception {
        var bodyVacioJSON = "{}";

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(bodyVacioJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede estar vacío"))
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede estar vacío"))
                .andExpect(jsonPath("$.codigos").value("El producto debe tener al menos un código"));
    }

    @Test
    @DisplayName("Crear producto con campos vacíos devuelve 400")
    void crearProductoConCamposVacios() throws Exception {
        var bodyCamposVacios = """
                {
                    "idProducto": "",
                    "nombreComercial": "",
                    "nombreGenerico": ""
                }
                """;

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(bodyCamposVacios))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede estar vacío"))
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con campos con tipo incorrecto devuelve 400")
    void crearProductoConCamposTipoIncorrecto() throws Exception {
        var bodyTipoIncorrecto = """
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
    @DisplayName("Crear producto válido devuelve 201")
    void crearProductoValido() throws Exception {
        var dto = productoValido;

        var creado = new ProductoCreado(
                "P001",
                dto.nombreComercial(),
                true,
                null,
                List.of(new CodigoCreado("C001", "1234567890123"))
        );

        when(servicioAppProducto.crearProducto(any(ProductoACrear.class)))
                .thenReturn(creado);

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/productos/P001"))
                .andExpect(jsonPath("$.idProducto").value("P001"))
                .andExpect(jsonPath("$.nombreComercial").value("Paracetamol"))
                .andExpect(jsonPath("$.codigos[0].codigoBarra").value("1234567890123"));
    }

    @Test
    @DisplayName("Actualizar producto válido devuelve 200")
    void actualizarProductoValido() throws Exception {
        var idProducto = "P001";

        var dto = new ProductoAModificar(
                "Nuevo Nombre",
                null,
                null,
                null,
                null,
                10,
                50,
                false,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(
                        new CodigoAModificar("C001", "1234567890123", null, true),
                        new CodigoAModificar("C002", "7800987654321", null, true)
                )
        );

        var actualizado = new ProductoModificado(
                dto.nombreComercial(),
                dto.activo(),
                null,
                List.of(
                        new CodigoModificado("C001", "1234567890123"),
                        new CodigoModificado("C002", "7800987654321")
                )
        );

        when(servicioAppProducto.actualizarProducto(Mockito.eq(idProducto), any(ProductoAModificar.class)))
                .thenReturn(actualizado);

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreComercial").value("Nuevo Nombre"))
                .andExpect(jsonPath("$.activo").value(false))
                .andExpect(jsonPath("$.codigos[0].idCodigo").value("C001"))
                .andExpect(jsonPath("$.codigos[0].codigoBarra").value("1234567890123"))
                .andExpect(jsonPath("$.codigos[1].idCodigo").value("C002"))
                .andExpect(jsonPath("$.codigos[1].codigoBarra").value("7800987654321"));
    }

    @Test
    @DisplayName("Actualizar los códigos de un producto válido devuelve 200")
    void actualizarCodigosProductoValido() throws Exception {
        var idProducto = "P001";

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
                List.of(
                        new CodigoAModificar("C001", "1234567890123", null, true),
                        new CodigoAModificar("C002", "7800987654321", null, true)
                )
        );

        var actualizado = new ProductoModificado(
                dto.nombreComercial(),
                dto.activo(),
                null,
                List.of(
                        new CodigoModificado("C001", "1234567890123"),
                        new CodigoModificado("C002", "7800987654321")
                )
        );

        when(servicioAppProducto.actualizarProducto(
                Mockito.eq(idProducto),
                any(ProductoAModificar.class)))
                .thenReturn(actualizado);

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreComercial").value("Nuevo Nombre"))
                .andExpect(jsonPath("$.activo").value(false))
                .andExpect(jsonPath("$.codigos[0].idCodigo").value("C001"))
                .andExpect(jsonPath("$.codigos[0].codigoBarra").value("1234567890123"))
                .andExpect(jsonPath("$.codigos[1].idCodigo").value("C002"))
                .andExpect(jsonPath("$.codigos[1].codigoBarra").value("7800987654321"));
    }

    @Test
    @DisplayName("Actualizar producto inexistente devuelve 404")
    void actualizarProductoNoExistente() throws Exception {
        var idProductoInexistente = "P999";

        var dto = new ProductoAModificar(
                "Paracetamol Modificado",
                "Paracetamol",
                "Tabletas 500mg",
                "500mg",
                "Comprimidos",
                10,
                100,
                true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        when(servicioAppProducto.actualizarProducto(Mockito.eq(idProductoInexistente), any(ProductoAModificar.class)))
                .thenThrow(new ProductoNoEncontradoException(idProductoInexistente));

        mockMvc.perform(patch("/productos/{id}", idProductoInexistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Actualizar producto con código inexistente devuelve 404")
    void actualizarProductoConCodigoNoExistente() throws Exception {
        var idProducto = "P001";

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
                List.of(
                        new CodigoAModificar("C999", "0000000000000", "EAN", true)
                )
        );

        when(servicioAppProducto.actualizarProducto(Mockito.eq(idProducto), any(ProductoAModificar.class)))
                .thenThrow(new CodigoNoEncontradoException("C999"));

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Actualizar producto con código que no pertenece al producto devuelve 404")
    void actualizarProductoConCodigoNoPertenece() throws Exception {
        var idProducto = "P001";

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
                List.of(
                        new CodigoAModificar("C002", "7800987654321", "EAN", true)
                )
        );

        when(servicioAppProducto.actualizarProducto(Mockito.eq(idProducto), any(ProductoAModificar.class)))
                .thenThrow(new CodigoNoPerteneceProductoException("C002", idProducto));

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Actualizar producto con lista de códigos nula no modifica códigos y devuelve 200")
    void actualizarProductoConCodigosNulosNoModifica() throws Exception {
        var idProducto = "P001";

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
                null
        );

        when(servicioAppProducto.actualizarProducto(Mockito.eq(idProducto), any(ProductoAModificar.class)))
                .thenReturn(new ProductoModificado(
                        dto.nombreComercial(),
                        dto.activo(),
                        null,
                        List.of(new CodigoModificado("C001", "1234567890123"))
                ));

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Crear producto con nombre comercial vacío devuelve 400")
    void crearProductoConNombreComercialVacio() throws Exception {
        var dto = new ProductoACrear(
                "",
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con nombre genérico vacío devuelve 400")
    void crearProductoConNombreGenericoVacio() throws Exception {
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                "",
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con stock máximo menor que stock mínimo devuelve 400")
    void crearProductoConStockMaxMenorQueStockMin() throws Exception {
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                50,
                10,
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['stockValido']").value("El stock máximo debe ser mayor o igual al stock mínimo"));
    }

    @Test
    @DisplayName("Crear producto con nombre comercial muy largo devuelve 400")
    void crearProductoConNombreComercialMuyLargo() throws Exception {
        var nombreComercial = "a".repeat(201);
        var dto = new ProductoACrear(
                nombreComercial,
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede tener más de 200 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con nombre genérico muy largo devuelve 400")
    void crearProductoConNombreGenericoMuyLargo() throws Exception {
        var nombreGen = "a".repeat(300);
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                nombreGen,
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede tener más de 200 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con presentación muy larga devuelve 400")
    void crearProductoConPresentacionMuyLarga() throws Exception {
        var presentacion = "a".repeat(501);
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                presentacion,
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.presentacion").value("La presentación no puede tener más de 500 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con dosificación muy larga devuelve 400")
    void crearProductoConDosificacionMuyLarga() throws Exception {
        var dosificacion = "a".repeat(101);
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                dosificacion,
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.dosificacion").value("La dosificación no puede tener más de 100 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con unidad de medida muy larga devuelve 400")
    void crearProductoConUnidadMedidaMuyLarga() throws Exception {
        var unidad = "a".repeat(51);
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                unidad,
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.unidadMedida").value("La unidad de medida no puede tener más de 50 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con stock máximo negativo y menor que stock mínimo devuelve 400")
    void crearProductoConStockMaximoNegativoYMenorQueMin() throws Exception {
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                10,
                -1,
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['stockMaximo']").value("El stock máximo no puede ser negativo"))
                .andExpect(jsonPath("$.['stockValido']").value("El stock máximo debe ser mayor o igual al stock mínimo"));
    }

    @Test
    @DisplayName("Crear producto con stock mínimo negativo devuelve 400")
    void crearProductoConStockMinimoNegativo() throws Exception {
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                -1,
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['stockMinimo']").value("El stock mínimo no puede ser negativo"));
    }

    @Test
    @DisplayName("Crear producto sin código devuelve devuelve 400")
    void crearProductoSinCodigos() throws Exception {
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                List.of()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigos").value("El producto debe tener al menos un código"));
    }

    @Test
    @DisplayName("Crear producto con id código vacío devuelve 400")
    void crearProductoConIdCodigoVacio() throws Exception {
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
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
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                List.of(new CodigoACrear("C001", "", "EAN", true))
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
        var codigoLargo = "1".repeat(101);
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                List.of(new CodigoACrear("C001", codigoLargo, "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].codigoBarra']").value("El código de barra no puede tener más de 100 caracteres"));
    }

    @Test
    @DisplayName("Crear producto con tipo de código vacío devuelve 400")
    void crearProductoConTipoCodigoVacio() throws Exception {
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                List.of(new CodigoACrear("C001", "1234567890123", "", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].tipoCodigo']").value("El tipo de código no puede estar vacío"));
    }

    @Test
    @DisplayName("Crear producto con tipo de código demasiado largo devuelve 400")
    void crearProductoConTipoCodigoMuyLargo() throws Exception {
        var tipoLargo = "a".repeat(51);
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                List.of(new CodigoACrear("C001", "1234567890123", tipoLargo, true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].tipoCodigo']").value("El tipo de código no puede tener más de 50 caracteres"));
    }

    @Test
    @DisplayName("Actualizar producto con stock máximo menor que stock mínimo devuelve 400")
    void actualizarProductoStockMaxMenorQueStockMin() throws Exception {
        var idProducto = "P001";

        var dto = new ProductoAModificar(
                "Nombre Nuevo",
                null,
                null,
                null,
                null,
                50,
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
                .andExpect(jsonPath("$.['stockValido']").value("El stock máximo debe ser mayor o igual al stock mínimo"));
    }

    @Test
    @DisplayName("Actualizar producto con stock mínimo negativo devuelve 400")
    void actualizarProductoConStockMinimoNegativo() throws Exception {
        var idProducto = "P001";

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
        var idProducto = "P001";

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
        var idProducto = "P001";
        var nombreLargo = "a".repeat(201);

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
    void actualizarProductoNombreGenericolMuyLargo() throws Exception {
        var idProducto = "P001";
        var nombreLargo = "a".repeat(201);

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
        var idProducto = "P001";
        var presentacionLarga = "a".repeat(501);

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
        var idProducto = "P001";
        var dosificacionLarga = "a".repeat(101);

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
        var idProducto = "P001";
        var unidadLarga = "a".repeat(51);

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
        var idProducto = "P001";

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
        var idProducto = "P001";

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

        when(servicioAppProducto.actualizarProducto(Mockito.eq(idProducto), any(ProductoAModificar.class)))
                .thenThrow(new CodigoNoPerteneceProductoException("C999", idProducto));

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Actualizar código con id vacío devuelve 400")
    void actualizarCodigoIdVacio() throws Exception {
        var idProducto = "P001";

        var dto = new CodigoAModificar(
                "",
                "1234567890123",
                "EAN",
                true
        );

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
        var idProducto = "P001";
        var codigoBarraLargo = "a".repeat(101);

        var dto = new CodigoAModificar(
                "C001",
                codigoBarraLargo,
                "EAN",
                true
        );

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
        var idProducto = "P001";
        var tipoCodigoLargo = "a".repeat(51);

        var dto = new CodigoAModificar(
                "C001",
                "1234567890123",
                tipoCodigoLargo,
                true
        );

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
        var idProducto = "P001";

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
                "Producto A",
                "Genérico A",
                "Presentación",
                "Dosificación",
                "Unidad",
                10,
                50,
                true,
                null,
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
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                100_000_001,
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.stockMinimo").value("El stock mínimo no puede superar 100.000.000"));
    }

    @Test
    @DisplayName("Crear producto con stock máximo excedido devuelve 400")
    void crearProductoConStockMaximoExcedido() throws Exception {
        var dto = new ProductoACrear(
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                100_000_001,
                productoValido.activo(),
                productoValido.categoria(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
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

        var dto = new ProductoACrear(
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
    @DisplayName("Buscar productos sin filtros devuelve todos los productos con urlFoto")
    void buscarProductosSinFiltros() throws Exception {
        Mockito.when(servicioAppProducto.buscarProductosFiltrados(null, null, null, null, 0))
                .thenReturn(new PageImpl<>(productosFiltrados, PageRequest.of(0, 4), productosFiltrados.size()));

        mockMvc.perform(get("/productos/buscar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(productosFiltrados.size()))
                .andExpect(jsonPath("$.content[0].idProducto").value("P001"))
                .andExpect(jsonPath("$.content[0].stockTotal").value(300))
                .andExpect(jsonPath("$.content[0].disponible").value(true))
                .andExpect(jsonPath("$.content[0].urlFoto").value("productos/P001.jpg"))
                .andExpect(jsonPath("$.content[1].idProducto").value("P002"))
                .andExpect(jsonPath("$.content[1].stockTotal").value(100))
                .andExpect(jsonPath("$.content[1].disponible").value(true))
                .andExpect(jsonPath("$.content[1].urlFoto").value("productos/P002.jpg"))
                .andExpect(jsonPath("$.content[2].idProducto").value("P003"))
                .andExpect(jsonPath("$.content[2].stockTotal").value(20))
                .andExpect(jsonPath("$.content[2].disponible").value(false))
                .andExpect(jsonPath("$.content[2].urlFoto").value("productos/P003.jpg"));
    }

    @Test
    @DisplayName("Buscar productos por nombre comercial devuelve coincidencias")
    void buscarProductosPorNombreComercial() throws Exception {
        var filtrados = productosFiltrados.stream()
                .filter(p -> p.nombreComercial().equalsIgnoreCase("Advil"))
                .toList();

        Mockito.when(servicioAppProducto.buscarProductosFiltrados("Advil", null, null, null, 0))
                .thenReturn(new PageImpl<>(filtrados, PageRequest.of(0, 4), filtrados.size()));

        mockMvc.perform(get("/productos/buscar")
                        .param("nombreComercial", "Advil"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].nombreComercial").value("Advil"))
                .andExpect(jsonPath("$.content[0].stockTotal").value(100))
                .andExpect(jsonPath("$.content[0].disponible").value(true))
                .andExpect(jsonPath("$.content[0].urlFoto").value("productos/P002.jpg"));
    }

    @Test
    @DisplayName("Buscar productos por nombre genérico devuelve coincidencias")
    void buscarProductosPorNombreGenerico() throws Exception {
        var filtrados = productosFiltrados.stream()
                .filter(p -> p.nombreGenerico().equalsIgnoreCase("Paracetamol"))
                .toList();

        Mockito.when(servicioAppProducto.buscarProductosFiltrados(null, "Paracetamol", null, null, 0))
                .thenReturn(new PageImpl<>(filtrados, PageRequest.of(0, 4), filtrados.size()));

        mockMvc.perform(get("/productos/buscar")
                        .param("nombreGenerico", "Paracetamol"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].nombreGenerico").value("Paracetamol"))
                .andExpect(jsonPath("$.content[0].stockTotal").value(300))
                .andExpect(jsonPath("$.content[0].disponible").value(true))
                .andExpect(jsonPath("$.content[0].urlFoto").value("productos/P001.jpg"));
    }

    @Test
    @DisplayName("Buscar productos por categoría devuelve coincidencias")
    void buscarProductosPorCategoria() throws Exception {
        var categoria = CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS.getNombreLegible();

        var filtrados = productosFiltrados.stream()
                .filter(p -> p.categoria().equalsIgnoreCase(categoria))
                .toList();

        Mockito.when(servicioAppProducto.buscarProductosFiltrados(
                null,
                null,
                null,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                0
        )).thenReturn(new PageImpl<>(
                filtrados,
                PageRequest.of(0, 4),
                filtrados.size()
        ));

        mockMvc.perform(get("/productos/buscar")
                        .param("categoria", CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(filtrados.size()))
                .andExpect(jsonPath("$.content[0].categoria").value(categoria));
    }

    @Test
    @DisplayName("Buscar productos por activo devuelve coincidencias")
    void buscarProductosPorActivo() throws Exception {
        var filtrados = productosFiltrados.stream()
                .filter(ProductoFiltrado::activo)
                .toList();

        Mockito.when(servicioAppProducto.buscarProductosFiltrados(null, null, true, null, 0))
                .thenReturn(new PageImpl<>(filtrados, PageRequest.of(0, 4), filtrados.size()));

        mockMvc.perform(get("/productos/buscar")
                        .param("activo", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].activo").value(true))
                .andExpect(jsonPath("$.content[0].stockTotal").value(300))
                .andExpect(jsonPath("$.content[0].disponible").value(true))
                .andExpect(jsonPath("$.content[0].urlFoto").value("productos/P001.jpg"))
                .andExpect(jsonPath("$.content[1].activo").value(true))
                .andExpect(jsonPath("$.content[1].stockTotal").value(100))
                .andExpect(jsonPath("$.content[1].disponible").value(true))
                .andExpect(jsonPath("$.content[1].urlFoto").value("productos/P002.jpg"));
    }

    @Test
    @DisplayName("Buscar productos sin coincidencias devuelve lista vacía")
    void buscarProductosSinCoincidencias() throws Exception {
        Mockito.when(servicioAppProducto.buscarProductosFiltrados("NoExiste", null, null, null, 0))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 4), 0));

        mockMvc.perform(get("/productos/buscar")
                        .param("nombreComercial", "NoExiste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(0));
    }

    @Test
    @DisplayName("Crear producto (JSON) y luego subir imagen funciona (200)")
    void crearProductoYLuegoSubirImagen() throws Exception {
        var dto = new ProductoACrear(
                "Prueba Producto", "Genérico Prueba",
                "Caja", "50mg", "mg",
                5, 100, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                List.of(new CodigoACrear("C020", "0001112223334", "EAN", true))
        );

        var creado = new ProductoCreado(
                "P020",
                dto.nombreComercial(),
                true,
                null,
                List.of(new CodigoCreado("C020", "0001112223334"))
        );

        when(servicioAppProducto.crearProducto(any(ProductoACrear.class)))
                .thenReturn(creado);

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

        doNothing().when(servicioAppProducto).actualizarFoto(eq("P020"), any(MultipartFile.class));

        mockMvc.perform(multipart("/productos/{id}/foto", "P020")
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Actualizar imagen del producto llama a servicio y devuelve 200")
    void actualizarImagenDelProducto() throws Exception {
        var foto = new MockMultipartFile(
                "foto",
                "ok.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "bytes".getBytes()
        );

        doNothing().when(servicioAppProducto).actualizarFoto("P001", foto);

        mockMvc.perform(multipart("/productos/{id}/foto", "P001")
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(servicioAppProducto).actualizarFoto(eq("P001"), any(MultipartFile.class));
    }

    @Test
    @DisplayName("Actualizar imagen con formato no permitido devuelve 400 y mensaje")
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
                .andExpect(jsonPath("$.foto").value("El archivo debe ser una imagen PNG o JPG"));
    }

    @Test
    @DisplayName("Actualizar imagen con nombre vacío devuelve 400 y mensaje")
    void actualizarImagenNombreVacio() throws Exception {
        var foto = new MockMultipartFile(
                "foto",
                "  ",
                MediaType.IMAGE_JPEG_VALUE,
                "bytes".getBytes()
        );

        mockMvc.perform(multipart("/productos/{id}/foto", "P001")
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.foto").value("El archivo debe tener un nombre válido"));
    }

    @Test
    @DisplayName("Error del servicio al guardar la imagen devuelve 500 o el código configurado")
    void actualizarImagenErrorInterno() throws Exception {
        var foto = new MockMultipartFile(
                "foto",
                "ok.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "bytes".getBytes()
        );

        doThrow(new ImagenAlmacenadaException())
                .when(servicioAppProducto)
                .actualizarFoto(eq("P001"), any(MultipartFile.class));

        mockMvc.perform(multipart("/productos/{id}/foto", "P001")
                        .file(foto)
                        .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Actualizar imagen sin enviar archivo devuelve 400 y mensaje")
    void actualizarImagenSinArchivo() throws Exception {
        mockMvc.perform(multipart("/productos/{id}/foto", "P001")
                        .with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.foto").value("El archivo no puede estar vacío"));
    }

    @Test
    @DisplayName("Actualizar imagen con archivo vacío devuelve 400 y mensaje")
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
                .andExpect(jsonPath("$.foto").value("El archivo no puede estar vacío"));
    }

    @Test
    @DisplayName("Obtener producto por ID devuelve 200 y el producto esperado")
    void obtenerProductoPorId() throws Exception {
        var codigos = List.of(new CodigoBuscado("C001", "1234567890123"));
        var productoBuscado = new ProductoBuscado(
                "P001",
                "Producto Test",
                "Genérico Test",
                "Caja",
                "50mg",
                "mg",
                true,
                10,
                null,
                codigos
        );

        when(servicioAppProducto.obtenerProductoPorId("P001")).thenReturn(productoBuscado);

        mockMvc.perform(get("/productos/{id}", "P001")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value("P001"))
                .andExpect(jsonPath("$.nombreComercial").value("Producto Test"))
                .andExpect(jsonPath("$.stockTotal").value(10))
                .andExpect(jsonPath("$.codigos[0].idCodigo").value("C001"));
    }

    @Test
    @DisplayName("Obtener producto por ID inexistente devuelve 404")
    void obtenerProductoPorIdNoExistente() throws Exception {
        when(servicioAppProducto.obtenerProductoPorId("P999"))
                .thenThrow(new ProductoNoEncontradoException("P999"));

        mockMvc.perform(get("/productos/{id}", "P999").with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se encontró producto con id = P999"));
    }
}