package cl.ufro.dci.pds.inventario.app.controladores;

import cl.ufro.dci.pds.inventario.app.dtos.MovimientoBuscado;
import cl.ufro.dci.pds.inventario.app.dtos.TrazabilidadIngreso;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppInventario;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.MovimientoNoEncontradoException;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.TipoMovimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trazabilidad")
public class ControladorTrazabilidad {

    private final ServicioAppInventario servicioAppInventario;

    public ControladorTrazabilidad(ServicioAppInventario servicioAppInventario) {
        this.servicioAppInventario = servicioAppInventario;
    }

    @GetMapping("/ingresos")
    public ResponseEntity<Page<TrazabilidadIngreso>> obtenerIngresos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
    ) {

        var pageable = PageRequest.of(page, size);
        var resultado = servicioAppInventario.obtenerIngresos(pageable);

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/movimientos/{id}")
    public ResponseEntity<MovimientoBuscado> buscarMovimiento(@PathVariable String id) {
        var resultado = servicioAppInventario.obtenerMovimiento(id);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/movimientos")
    public ResponseEntity<Page<MovimientoBuscado>> buscarMovimientos(
            @RequestParam(required = false) TipoMovimiento tipoMovimiento,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var resultados = servicioAppInventario.obtenerMovimientosPor(tipoMovimiento, page, size);
        return ResponseEntity.ok(resultados);
    }

    @ExceptionHandler({MovimientoNoEncontradoException.class})
    public ResponseEntity<String> manejarNoEncontrado(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
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

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<?> manejarParteFaltante(MissingServletRequestPartException ex) {
        return ResponseEntity.badRequest().body(
                Map.of("errors", Map.of(
                        ex.getRequestPartName(), "El archivo no puede estar vacío"
                ))
        );
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
