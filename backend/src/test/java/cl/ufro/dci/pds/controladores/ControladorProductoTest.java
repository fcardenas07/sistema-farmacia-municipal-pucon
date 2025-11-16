package cl.ufro.dci.pds.controladores;

import cl.ufro.dci.pds.infraestructura.SecurityConfig;
import cl.ufro.dci.pds.inventario.app.controladores.ControladorProducto;
import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoDuplicadoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoNoEncontradoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoNoPerteneceProductoException;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    void setUp() {
        repositorioProducto.deleteAll();
        productoValido = new ProductoACrear(
                "P001",
                "Paracetamol",
                "Paracetamol genérico",
                "Caja con 20 tabletas",
                "500mg",
                "mg",
                10,
                100,
                true,
                List.of(new CodigoACrear("C001", "1234567890123", "EAN", true),
                        new CodigoACrear("C002", "7800987654321", "EAN13", true))
        );
    }

    @Test
    void crearProductoConBodyNulo() throws Exception {
        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Crear producto válido devuelve 201")
    void crearProductoValido() throws Exception {
        var dto = productoValido;

        var creado = new ProductoCreado(
                dto.idProducto(),
                dto.nombreComercial(),
                true,
                List.of(new CodigoCreado("C001", "1234567890123"))
        );

        Mockito.when(servicioAppProducto.crearProducto(Mockito.any(ProductoACrear.class)))
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
    @DisplayName("Crear producto con código duplicado devuelve 409")
    void crearProductoCodigoDuplicado() throws Exception {
        ProductoACrear dto = new ProductoACrear(
                "P002",
                "Ibuprofeno",
                "Ibuprofeno genérico",
                "Tabletas 400mg",
                "400mg",
                "Comprimidos",
                5,
                50,
                true,
                List.of(new CodigoACrear("C002", "9876543210987", "EAN", true))
        );

        Mockito.when(servicioAppProducto.crearProducto(Mockito.any(ProductoACrear.class)))
                .thenThrow(new CodigoDuplicadoException("C002"));

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
                List.of(
                        new CodigoAModificar("C001", "1234567890123", null, true),
                        new CodigoAModificar("C002", "7800987654321", null, true)
                )
        );

        var actualizado = new ProductoModificado(
                dto.nombreComercial(),
                dto.activo(),
                List.of(
                        new CodigoModificado("C001", "1234567890123"),
                        new CodigoModificado("C002", "7800987654321")
                )
        );

        Mockito.when(servicioAppProducto.actualizarProducto(Mockito.eq(idProducto), Mockito.any(ProductoAModificar.class)))
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
                List.of(
                        new CodigoAModificar("C001", "1234567890123", null, true),
                        new CodigoAModificar("C002", "7800987654321", null, true)
                )
        );

        var actualizado = new ProductoModificado(
                dto.nombreComercial(),
                dto.activo(),
                List.of(
                        new CodigoModificado("C001", "1234567890123"),
                        new CodigoModificado("C002", "7800987654321")
                )
        );

        Mockito.when(servicioAppProducto.actualizarProducto(
                        Mockito.eq(idProducto),
                        Mockito.any(ProductoAModificar.class)))
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
                List.of(new CodigoAModificar("C001", "1234567890123", "EAN", true))
        );

        Mockito.when(servicioAppProducto.actualizarProducto(Mockito.eq(idProductoInexistente), Mockito.any(ProductoAModificar.class)))
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
                List.of(
                        new CodigoAModificar("C999", "0000000000000", "EAN", true)
                )
        );

        Mockito.when(servicioAppProducto.actualizarProducto(Mockito.eq(idProducto), Mockito.any(ProductoAModificar.class)))
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
                List.of(
                        new CodigoAModificar("C002", "7800987654321", "EAN", true) // este código no pertenece al producto P001
                )
        );

        Mockito.when(servicioAppProducto.actualizarProducto(Mockito.eq(idProducto), Mockito.any(ProductoAModificar.class)))
                .thenThrow(new CodigoNoPerteneceProductoException("C002", idProducto));

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
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
                List.of(new CodigoACrear("C001", "1234567890123", "EAN", true))
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.idProducto").value("El idProducto no puede ser vacío"));
    }

    @Test
    @DisplayName("Crear producto con nombre comercial vacío devuelve 400")
    void crearProductoConNombreComercialVacio() throws Exception {
        var dto = new ProductoACrear(
                productoValido.idProducto(),
                "",
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreComercial").value("El nombre comercial no puede ser vacío"));
    }

    @Test
    @DisplayName("Crear producto con nombre genérico vacío devuelve 400")
    void crearProductoConNombreGenericoVacio() throws Exception {
        var dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                "",
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.nombreGenerico").value("El nombre genérico no puede ser vacío"));
    }

    @Test
    @DisplayName("Crear producto con stock negativo devuelve 400")
    void crearProductoConStockNegativo() throws Exception {
        ProductoACrear dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                -1,
                productoValido.stockMaximo(),
                productoValido.activo(),
                productoValido.codigos()
        );

        mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.stockMinimo").value("El stock mínimo no puede ser negativo"));
    }

    @Test
    @DisplayName("Crear producto con stock máximo menor que stock mínimo devuelve 400")
    void crearProductoConStockMaxMenorQueStockMin() throws Exception {
        ProductoACrear dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                50,
                10,
                productoValido.activo(),
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
        ProductoACrear dto = new ProductoACrear(
                productoValido.idProducto(),
                nombreComercial,
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
        ProductoACrear dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                nombreGen,
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
        ProductoACrear dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                presentacion,
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
        String dosificacion = "a".repeat(101);
        ProductoACrear dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                dosificacion,
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
        String unidad = "a".repeat(51);
        ProductoACrear dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                unidad,
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
        ProductoACrear dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                10,
                -1,
                productoValido.activo(),
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
        ProductoACrear dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                -1,
                productoValido.stockMaximo(),
                productoValido.activo(),
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
        ProductoACrear dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
        String codigoLargo = "1".repeat(101);
        var dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
        String tipoLargo = "a".repeat(51);
        var dto = new ProductoACrear(
                productoValido.idProducto(),
                productoValido.nombreComercial(),
                productoValido.nombreGenerico(),
                productoValido.presentacion(),
                productoValido.dosificacion(),
                productoValido.unidadMedida(),
                productoValido.stockMinimo(),
                productoValido.stockMaximo(),
                productoValido.activo(),
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
                List.of(new CodigoAModificar("C999", "0000000000000", "EAN", true))
        );

        Mockito.when(servicioAppProducto.actualizarProducto(Mockito.eq(idProducto), Mockito.any(ProductoAModificar.class)))
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
        String idProducto = "P001";

        var dto = new CodigoAModificar(
                "",
                "1234567890123",
                "EAN",
                true
        );

        var productoDto = new ProductoAModificar(
                null, null, null, null, null,
                10, 50, true,
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
        String codigoBarraLargo = "a".repeat(101);

        var dto = new CodigoAModificar(
                "C001",
                codigoBarraLargo,
                "EAN",
                true
        );

        var productoDto = new ProductoAModificar(
                null, null, null, null, null,
                10, 50, true,
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

        var dto = new CodigoAModificar(
                "C001",
                "1234567890123",
                tipoCodigoLargo,
                true
        );

        var productoDto = new ProductoAModificar(
                null, null, null, null, null,
                10, 50, true,
                List.of(dto)
        );

        mockMvc.perform(patch("/productos/{id}", idProducto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(productoDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.['codigos[0].tipoCodigo']").value("El tipo de código no puede tener más de 50 caracteres"));
    }
}
