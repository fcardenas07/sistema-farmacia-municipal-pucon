package cl.ufro.dci.pds.inventario.app.controladores;

import cl.ufro.dci.pds.inventario.app.dtos.*;

import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppInventario;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventario")
public class ControladorInventario {

    private final ServicioAppInventario servicioAppInventario;

    public ControladorInventario(ServicioAppInventario servicioAppInventario) {
        this.servicioAppInventario = servicioAppInventario;
    }

    @PostMapping
    public ResponseEntity<EntradaIngresada> crear(@Valid @RequestBody EntradaInventario dto) {
        var ingresado = servicioAppInventario.crearLote(dto);

        return ResponseEntity
                .created(URI.create("/inventario/" + ingresado.idLote()))
                .body(ingresado);
    }

    @PostMapping("/mermas")
    public ResponseEntity<Void> registrarMerma(@Valid @RequestBody IngresoMerma dto) {
        servicioAppInventario.ingresarMerma(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/lotes/buscar")
    public ResponseEntity<List<LoteSimple>> buscarLotes(@RequestParam(required = false) String filtro) {
        return ResponseEntity.ok(servicioAppInventario.obtenerLotesPor(filtro));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErrorGeneral(Exception ex) {
        System.out.println(ex.getMessage());

        var body = Map.of("mensaje", "Error interno del servidor");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> manejarBodyFaltante(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Body de la solicitud requerido");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejarValidacion(MethodArgumentNotValidException ex) {

        var errores = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        e -> Optional.ofNullable(e.getDefaultMessage()).orElse("Error desconocido"),
                        (a, _) -> a
                ));

        System.out.println("Errores de validación: " + errores);
        return ResponseEntity.badRequest().body(errores);
    }
}
