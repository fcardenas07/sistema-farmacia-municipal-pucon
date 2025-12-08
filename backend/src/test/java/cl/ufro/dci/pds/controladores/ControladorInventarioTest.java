package cl.ufro.dci.pds.controladores;

import cl.ufro.dci.pds.infraestructura.SecurityConfig;
import cl.ufro.dci.pds.inventario.app.controladores.ControladorInventario;
import cl.ufro.dci.pds.inventario.app.dtos.EntradaInventario;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppInventario;
import cl.ufro.dci.pds.testmother.inventario.objectmother.EntradaIngresadaMother;
import cl.ufro.dci.pds.testmother.inventario.objectmother.EntradaInventarioMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControladorInventario.class)
@Import(SecurityConfig.class)
class ControladorInventarioTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicioAppInventario servicioAppInventario;

    @Test
    @DisplayName("Entrada inventario con un body vacío devuelve 400")
    void entradaInventarioConBodyNulo() throws Exception {
        mockMvc.perform(post("/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Entrada inventario con un body que no es un json devuelve 400")
    void entradaInventarioConBodyInvalidoFormato() throws Exception {
        var bodyInvalido = "esto no es JSON";
        mockMvc.perform(post("/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(bodyInvalido))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Entrada inventario con un json vacío devuelve 400")
    void entradaInventarioConBodyVacioJSON() throws Exception {
        var bodyVacioJSON = "{}";

        mockMvc.perform(post("/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(bodyVacioJSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.estado").value("El estado es obligatorio"))
                .andExpect(jsonPath("$.numeroLote").value("El número de lote es obligatorio"))
                .andExpect(jsonPath("$.precioUnitario").value("El precio unitario es obligatorio"))
                .andExpect(jsonPath("$.fechaVencimiento").value("La fecha de vencimiento es obligatoria"))
                .andExpect(jsonPath("$.cantidad").value("La cantidad es obligatoria"))
                .andExpect(jsonPath("$.fechaElaboracion").value("La fecha de elaboración es obligatoria"))
                .andExpect(jsonPath("$.codigo").value("El código es obligatorio"));
    }

    @Test
    @DisplayName("Entrada inventario con campos vacíos devuelve 400")
    void entradaInventarioConCamposVacios() throws Exception {
        var bodyCamposVacios = EntradaInventarioMother.jsonCamposVacios();

        mockMvc.perform(post("/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(bodyCamposVacios))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.estado").value("El estado es obligatorio"))
                .andExpect(jsonPath("$.numeroLote").value("El número de lote es obligatorio"))
                .andExpect(jsonPath("$.precioUnitario").value("El precio unitario es obligatorio"))
                .andExpect(jsonPath("$.fechaVencimiento").value("La fecha de vencimiento es obligatoria"))
                .andExpect(jsonPath("$.cantidad").value("La cantidad es obligatoria"))
                .andExpect(jsonPath("$.fechaElaboracion").value("La fecha de elaboración es obligatoria"))
                .andExpect(jsonPath("$.codigo").value("El código es obligatorio"));
    }

    @Test
    @DisplayName("Crear entrada inventario válida devuelve 201")
    void entradaInventarioValida() throws Exception {

        var json = EntradaInventarioMother.jsonValido();
        var creado = EntradaIngresadaMother.crearValida();

        when(servicioAppInventario.crearLote(any(EntradaInventario.class)))
                .thenReturn(creado);


        mockMvc.perform(post("/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(json))
                .andExpect(status().isCreated());
    }
}