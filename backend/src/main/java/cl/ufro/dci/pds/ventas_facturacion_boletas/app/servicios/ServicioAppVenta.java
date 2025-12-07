package cl.ufro.dci.pds.ventas_facturacion_boletas.app.servicios;

import cl.ufro.dci.pds.compartido.eventos.EventoResultadoPago;
import cl.ufro.dci.pds.infraestructura.BusEventosVentas;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.*;
import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.ServicioCliente;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.ServicioUsuario;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.ItemLoteCantidad;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.VentaACrear;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.VentaCreada;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ServicioAppVenta {

    private final ServicioVenta servicioVenta;
    private final ServicioUsuario servicioUsuario;
    private final ServicioCliente servicioCliente;
    private final ServicioLote servicioLote;
    private final VentasMapper ventasMapper;

    public ServicioAppVenta(ServicioVenta servicioVenta,
                            ServicioUsuario servicioUsuario,
                            ServicioCliente servicioCliente,
                            ServicioLote servicioLote,
                            VentasMapper ventasMapper) {
        this.servicioVenta = servicioVenta;
        this.servicioUsuario = servicioUsuario;
        this.servicioCliente = servicioCliente;
        this.servicioLote = servicioLote;
        this.ventasMapper = ventasMapper;
    }

    @Transactional
    public VentaCreada crear(@Valid VentaACrear dto) {
        var venta = crearVentaConDetalles(dto);
        reservarLotes(venta);
        servicioVenta.guardar(venta);
        return ventasMapper.toDto(venta);
    }

    private Venta crearVentaConDetalles(VentaACrear dto) {
        var usuario = servicioUsuario.buscarPorId(dto.idVendedor());
        var cliente = servicioCliente.buscarPorRut(dto.rutCliente());

        var venta = servicioVenta.crear(
                usuario,
                cliente,
                LocalDateTime.now()
        );

        dto.detalleVenta().forEach(d -> {
            var lote = servicioLote.obtenerPorId(d.idLote());
            venta.agregarDetalle(lote, d.cantidad(), d.precioUnitario());
        });

        return venta;
    }

    private void reservarLotes(Venta venta) {
        var reservas = venta.getDetalles().stream()
                .map(detalle -> new ItemLoteCantidad(detalle.getLote(), detalle.getCantidad()))
                .toList();

        servicioLote.reservarLotes(reservas);
    }
}

