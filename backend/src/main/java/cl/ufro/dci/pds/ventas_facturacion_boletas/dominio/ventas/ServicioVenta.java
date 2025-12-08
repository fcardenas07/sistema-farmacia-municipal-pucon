package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.Cliente;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ServicioVenta {
    private final RepositorioVenta repositorioVenta;

    public ServicioVenta(RepositorioVenta repositorioVenta) {
        this.repositorioVenta = repositorioVenta;
    }

    public Venta crear(Usuario usuario,
                       Cliente cliente,
                       LocalDateTime fechaVenta) {

        var venta = new Venta();
        venta.setFechaVenta(fechaVenta);
        venta.setEstadoVenta(EstadoVenta.PENDIENTE_PAGO);
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        return guardar(venta);
    }

    public Venta guardar(Venta venta) {
        return repositorioVenta.save(venta);
    }

    public Venta buscarPorId(String id) {
        return repositorioVenta.findById(id)
            .orElseThrow(() ->
            new VentaNoEncontradaException(id));
    }
}
