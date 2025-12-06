package cl.ufro.dci.pds.ventas_facturacion_boletas.app.servicios;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.*;
import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.ServicioCliente;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.ServicioUsuario;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.VentaACrear;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.VentaCreada;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.VentasMapper;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.ServicioVenta;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class ServicioAppVenta {

    private final ServicioVenta servicioVenta;
    private final ServicioUsuario servicioUsuario;
    private final ServicioCliente servicioCliente;
    private final ServicioLote servicioLote;
    private final VentasMapper ventasMapper;


    public ServicioAppVenta(ServicioVenta servicioVenta, ServicioUsuario servicioUsuario, ServicioCliente servicioCliente, ServicioLote servicioLote, VentasMapper ventasMapper) {
        this.servicioVenta = servicioVenta;
        this.servicioUsuario = servicioUsuario;
        this.servicioCliente = servicioCliente;
        this.servicioLote = servicioLote;
        this.ventasMapper = ventasMapper;
    }

    @Transactional
    public VentaCreada crear(@Valid VentaACrear dto) {
        var usuario = servicioUsuario.buscarPorId(dto.idVendedor());
        var cliente = servicioCliente.buscarPorRut(dto.rutCliente());
        var solicitudes = dto.detalleVenta().stream()
                .map(det -> new SolicitudReservaLote(det.idLote(), det.cantidad()))
                .toList();

        var lotesReservados = servicioLote.reservarLotes(solicitudes);
        var venta = servicioVenta.crear(
                usuario,
                cliente,
                dto.fechaVenta(),
                dto.total(),
                lotesReservados,
                dto.detalleVenta()
        );
        return ventasMapper.toDto(venta);
    }
}

