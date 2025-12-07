package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetallesPago;

@Service
public class ServicioPago {

    private final RepositorioPago repositorioPago;
    
    public ServicioPago(RepositorioPago repositorioPago){
        this.repositorioPago = repositorioPago;
    }

    public Pago crear(Venta venta, DetallesPago dto) {
        Pago p = new Pago();
        p.setVenta(venta);
        p.setEstado(EstadoPago.PENDIENTE);
        p.setFecha_pago(LocalDateTime.now());
        p.setReferencia_transaccion(dto.referenciaTransaccion());
        p.setMetodo_pago(dto.metodoPago());
        p.setMonto(dto.monto());
        return repositorioPago.save(p);
    }

    public Pago actualizarEstado(String pagoId, EstadoPago nuevoEstado){
        var pago = repositorioPago.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        pago.setEstado(nuevoEstado);
        return repositorioPago.save(pago);
    }

}
