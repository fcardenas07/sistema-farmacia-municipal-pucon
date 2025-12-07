package cl.ufro.dci.pds.ventas_facturacion_boletas.app.controladores;

import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.*;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.servicios.ServicioAppPago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.servicios.ServicioAppVenta;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.facturacion.boletas.BoletaNoEncontradaException;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos.EstrategiaPagoNoEncontradaException;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.VentaNoEncontradaException;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ventas")
public class ControladorVenta {

    private final ServicioAppVenta servicioAppVenta;
    private final ServicioAppPago servicioAppPago;

    public ControladorVenta(ServicioAppVenta servicioAppVenta, ServicioAppPago servicioAppPago) {
        this.servicioAppVenta = servicioAppVenta;
        this.servicioAppPago = servicioAppPago;
    }

    @PostMapping
    public ResponseEntity<VentaCreada> crear(@Valid @RequestBody VentaACrear dto) {
        var ventaCreada = servicioAppVenta.crear(dto);
        return ResponseEntity
                .created(URI.create("/ventas/" + ventaCreada.idVenta()))
                .body(ventaCreada);
    }

    @PostMapping("/{id}/pago")
    public ResponseEntity<PagoProcesado> crearPago(@PathVariable String id,
                                                   @Valid @RequestBody DetallesPago dto) {
        var pagoProcesado = servicioAppPago.crearYProcesar(id, dto.metodoPago(), dto);

        return ResponseEntity
                .created(URI.create("/ventas/" + id + "/pago/" + pagoProcesado.idPago()))
                .body(pagoProcesado);
    }

    @GetMapping("/boletas/{idBoleta}/pdf")
    public ResponseEntity<byte[]> obtenerBoletaPdf(@PathVariable String idBoleta) {
        var boletaConPdf = servicioAppPago.obtenerBoletaConPdf(idBoleta);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=boleta-" + idBoleta + ".pdf")
                .body(boletaConPdf.pdf());
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

    @ExceptionHandler(EstrategiaPagoNoEncontradaException.class)
    public ResponseEntity<Map<String, String>> manejarEstrategiaNoEncontrada(EstrategiaPagoNoEncontradaException ex) {
        var body = Map.of(
                "mensaje", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(BoletaNoEncontradaException.class)
    public ResponseEntity<Map<String, String>> manejarBoletaNoEncontrada(BoletaNoEncontradaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", ex.getMessage()));
    }

    @ExceptionHandler(VentaNoEncontradaException.class)
    public ResponseEntity<Map<String, String>> manejarVentaNoEncontrada(VentaNoEncontradaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", ex.getMessage()));
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