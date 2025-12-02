package cl.ufro.dci.pds.inventario.app.controladores;

import cl.ufro.dci.pds.inventario.app.dtos.TrazabilidadIngreso;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppInventario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<TrazabilidadIngreso>> obtenerIngresos() {
        var lista = servicioAppInventario.obtenerIngresos();
        return ResponseEntity.ok(lista);
    }
}
