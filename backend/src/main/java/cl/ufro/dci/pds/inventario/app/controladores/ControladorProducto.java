package cl.ufro.dci.pds.inventario.app.controladores;

import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoDuplicadoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoNoEncontradoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoNoPerteneceProductoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ProductoDuplicadoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ProductoNoEncontradoException;
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
@RequestMapping("/productos")
public class ControladorProducto {

    private final ServicioAppProducto servicioAppProducto;

    public ControladorProducto(ServicioAppProducto servicioAppProducto) {
        this.servicioAppProducto = servicioAppProducto;
    }

    @PostMapping
    public ResponseEntity<ProductoCreado> crear(@Valid @RequestBody ProductoACrear dto) {
        var creado = servicioAppProducto.crearProducto(dto);
        return ResponseEntity
                .created(URI.create("/productos/" + creado.idProducto()))
                .body(creado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductoModificado> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ProductoAModificar dto
    ) {
        var actualizado = servicioAppProducto.actualizarProducto(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoFiltrado>> buscarProductos(
            @RequestParam(required = false) String idProducto,
            @RequestParam(required = false) String nombreComercial,
            @RequestParam(required = false) String nombreGenerico,
            @RequestParam(required = false) Boolean activo
    ) {
        var productos = servicioAppProducto.buscarProductosFiltrados(
                idProducto, nombreComercial, nombreGenerico, activo
        );

        return ResponseEntity.ok(productos);
    }

    @ExceptionHandler({CodigoDuplicadoException.class, ProductoDuplicadoException.class})
    public ResponseEntity<String> manejarConflicto(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler({ProductoNoEncontradoException.class, CodigoNoEncontradoException.class,
            CodigoNoPerteneceProductoException.class})
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
