package cl.ufro.dci.pds.inventario.app.controladores;

import cl.ufro.dci.pds.inventario.app.dtos.TrazabilidadIngreso;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppInventario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
