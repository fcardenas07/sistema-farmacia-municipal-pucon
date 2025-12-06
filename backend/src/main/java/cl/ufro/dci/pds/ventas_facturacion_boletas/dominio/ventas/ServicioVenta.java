package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;
import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.Cliente;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;

import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetalleVentaACrear;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class ServicioVenta {

    private final RepositorioVenta repositorioVenta;

    public ServicioVenta(RepositorioVenta repositorioVenta) {
        this.repositorioVenta = repositorioVenta;
    }

    public Venta crear(Usuario usuario,
                        Cliente cliente,
                        LocalDate fechaVenta,
                       List<DetalleVentaACrear> detalleVenta) {

        Venta venta = new Venta();
        venta.setFechaVenta(fechaVenta);
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        venta.setEstadoVenta(EstadoVenta.PENDIENTE_PAGO);
        return repositorioVenta.save(venta);
    }


    public Venta buscarPorId(String idVenta) {
        return repositorioVenta.findById(idVenta)
                .orElseThrow(() ->
                        new VentaNoEncontradaException(idVenta));
    }

    public Venta guardar(Venta venta) {
        return repositorioVenta.save(venta);
    }
}
