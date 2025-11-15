package cl.ufro.dci.pds.inventario.app.controladores;

import cl.ufro.dci.pds.inventario.app.dtos.ProductoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoCreado;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoModificado;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.CodigoDuplicadoException;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ProductoNoEncontradoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/productos")
public class ControladorProducto {

    private final ServicioAppProducto servicioAppProducto;

    public ControladorProducto(ServicioAppProducto servicioAppProducto) {
        this.servicioAppProducto = servicioAppProducto;
    }

    @PostMapping
    public ResponseEntity<ProductoCreado> crear(@RequestBody ProductoACrear dto) {
        try {
            var creado = servicioAppProducto.crearProducto(dto);
            return ResponseEntity
                    .created(URI.create("/productos/" + creado.idProducto()))
                    .body(creado);
        } catch (CodigoDuplicadoException ex) {
            return ResponseEntity.status(409).build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductoModificado> actualizar(
            @PathVariable String id,
            @RequestBody ProductoAModificar dto
    ) {
        try {
            var actualizado = servicioAppProducto.actualizarProducto(id, dto);
            return ResponseEntity.ok(actualizado);
        } catch (ProductoNoEncontradoException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}
