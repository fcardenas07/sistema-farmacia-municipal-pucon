package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

import cl.ufro.dci.pds.compartido.eventos.ItemVenta;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetalleVentaCreado;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.VentaCreada;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VentasMapper {

    public VentaCreada toDto(Venta venta) {
        return new VentaCreada(
                venta.getIdVenta(),
                venta.getEstadoVenta().toString(),
                venta.getFechaVenta().toString(),
                venta.getCliente().getRutCliente(),
                venta.getCliente().getNombre(),
                venta.getUsuario().getIdUsuario(),
                venta.getUsuario().getNombreCompleto(),
                venta.getTotal(),
                toDto(venta.getDetallesVenta())
        );
    }

    public List<DetalleVentaCreado> toDto(List<DetalleVenta> detalleVenta){
        if (detalleVenta == null || detalleVenta.isEmpty()) {
            return List.of();
        }
        return detalleVenta.stream()
                .map(det -> new DetalleVentaCreado(
                        det.getLote().getIdLote(),
                        det.getLote().getNumeroLote(),
                        det.getCantidad(),
                        det.getPrecioUnitario(),
                        det.getSubtotal()
                ))
                .toList();

    }
}
