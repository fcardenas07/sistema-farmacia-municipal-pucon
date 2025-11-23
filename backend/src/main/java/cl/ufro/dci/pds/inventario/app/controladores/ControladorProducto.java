package cl.ufro.dci.pds.inventario.app.controladores;

import cl.ufro.dci.pds.inventario.app.dtos.*;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoDuplicadoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoNoEncontradoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoNoPerteneceProductoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ProductoDuplicadoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ProductoNoEncontradoException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

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

    @PostMapping("/{id}/foto")
    public ResponseEntity<?> subirFoto(
            @PathVariable String id,
            @Valid @ModelAttribute FotoProductoASubir dto
    ) {
        servicioAppProducto.actualizarFoto(id, dto.foto());
        return ResponseEntity.ok(Map.of("mensaje", "Foto subida correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoBuscado> obtenerProducto(@PathVariable String id) {
        var resultado = servicioAppProducto.obtenerProductoPorId(id);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/buscar-stock-normal")
    public ResponseEntity<Page<ProductoFiltrado>> buscarProductosStockNormal(
            @RequestParam(required = false) String nombreComercial,
            @RequestParam(required = false) String nombreGenerico,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) CategoriaProducto categoria,
            @RequestParam(defaultValue = "0") int pagina
    ) {
        var productos = servicioAppProducto.buscarProductosFiltrados(
                nombreComercial, nombreGenerico, activo, categoria, pagina, "critico"
        );

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar-stock-critico")
    public ResponseEntity<Page<ProductoFiltrado>> buscarProductosStockCritico(
            @RequestParam(required = false) String nombreComercial,
            @RequestParam(required = false) String nombreGenerico,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) CategoriaProducto categoria,
            @RequestParam(defaultValue = "0") int pagina
    ) {
        var productos = servicioAppProducto.buscarProductosFiltrados(
                nombreComercial, nombreGenerico, activo, categoria, pagina, "normal"
        );

        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar-para-codigo")
    public ResponseEntity<List<ProductoParaCodigo>> buscarProductosParaCodigo(
            @RequestParam(required = false) String nombreComercial
    ) {
        var productos = servicioAppProducto.buscarProductosParaCodigo(nombreComercial);
        return ResponseEntity.ok(productos);
    }

    @PatchMapping("dar-de-baja/{id}")
    public ResponseEntity<Void> darBaja(@PathVariable String id) {
        servicioAppProducto.darBajaProducto(id);
        return ResponseEntity.ok().build();
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
