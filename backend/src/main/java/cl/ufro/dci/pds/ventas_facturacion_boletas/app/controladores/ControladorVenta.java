package cl.ufro.dci.pds.ventas_facturacion_boletas.app.controladores;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.LoteNoEncontradoException;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.VentaACrear;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.VentaCreada;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.servicios.ServicioAppPago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.servicios.ServicioAppVenta;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetallesPago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.PagoCreado;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.PagoProcesado;

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
    public ResponseEntity<VentaCreada> crear(@Valid @RequestBody VentaACrear dto){
        var ventaCreada = servicioAppVenta.crear(dto);
        return ResponseEntity
                .created(URI.create("/ventas/" + ventaCreada.idVenta()))
                .body(ventaCreada);
    }

    @PostMapping("/{id}/pago")
    public ResponseEntity<PagoProcesado> crearPago(
            @PathVariable String id,
            @Valid @RequestBody DetallesPago dto) {



        var pagoProcesado = servicioAppPago.crearYProcesar(
                id,
                dto.metodoPago(),
                dto
        );

    return ResponseEntity
            .created(URI.create("/ventas/" + id + "/pago/" + pagoProcesado.idPago()))
            .body(pagoProcesado);
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

    @ExceptionHandler(LoteNoEncontradoException.class)
    public ResponseEntity<String> manejarNoEncontrado(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
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