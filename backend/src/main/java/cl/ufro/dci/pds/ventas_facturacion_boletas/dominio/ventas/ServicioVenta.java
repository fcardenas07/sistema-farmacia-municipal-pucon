package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.Cliente;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetalleVentaACrear;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ServicioVenta {

    private final RepositorioVenta repositorioVenta;

    public ServicioVenta(RepositorioVenta repositorioVenta) {
        this.repositorioVenta = repositorioVenta;
    }

    public Venta crear(Usuario usuario,
                        Cliente cliente,
                        LocalDate fechaVenta,
                        Integer total,
                        List<Lote> lotesReservados,
                        List<DetalleVentaACrear> detalleVentaACrear) {

        Venta venta = new Venta();
        venta.setFechaVenta(fechaVenta);
        venta.setTotal(total);
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        venta.setEstadoVenta(EstadoVenta.PENDIENTE_PAGO);

        var lotesPorId = lotesReservados.stream()
                .collect(Collectors.toMap(Lote::getIdLote, Function.identity()));

        for (var det : detalleVentaACrear) {
            var lote = lotesPorId.get(det.idLote());

            if (lote == null) {
                throw new IllegalStateException("Lote reservado no encontrado: " + det.idLote());
            }

            venta.agregarDetalle(
                    lote,
                    det.cantidad(),
                    det.precioUnitario()
            );
        }

        venta.recalcularTotal();
        return repositorioVenta.save(venta);
    }
}
