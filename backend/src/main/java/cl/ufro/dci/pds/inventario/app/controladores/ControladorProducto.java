package cl.ufro.dci.pds.inventario.app.controladores;

import cl.ufro.dci.pds.inventario.app.dtos.NuevoProducto;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoActualizado;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoCreado;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoModificado;
import cl.ufro.dci.pds.inventario.app.servicios.ServicioAppProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ProductoDuplicadoException;
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
    public ResponseEntity<ProductoCreado> crear(@RequestBody NuevoProducto dto) {
        try {
            var creado = servicioAppProducto.crearProducto(dto);
            return ResponseEntity
                    .created(URI.create("/productos/" + creado.idProducto()))
                    .body(creado);
        } catch (ProductoDuplicadoException ex) {
            return ResponseEntity.status(409).build();
        } catch (Exception ex) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoActualizado> actualizar(
            @PathVariable String id,
            @RequestBody ProductoModificado productoModificado
    ) {
        try {
            var actualizado = servicioAppProducto.actualizarProducto(id, productoModificado);
            return ResponseEntity.ok(actualizado);
        } catch (ProductoNoEncontradoException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(500).build();
        }
    }
}
