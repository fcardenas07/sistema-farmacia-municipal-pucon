package cl.ufro.dci.pds.ventas_facturacion_boletas.app.servicios;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos.EstadoPago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos.Pago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos.PagoEstrategia;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos.ServicioPago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.ServicioVenta;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import jakarta.transaction.Transactional;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.DetallesPago;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.PagoProcesado;

@Service
public class ServicioAppPago {

    private final ServicioPago servicioPago;
    private final ServicioVenta servicioVenta;
    private final Map<String, PagoEstrategia> estrategias;

    ServicioAppPago(ServicioPago servicioPago, ServicioVenta servicioVenta, Map<String, PagoEstrategia> estrategias) {
        this.servicioPago = servicioPago;
        this.servicioVenta = servicioVenta;
        this.estrategias = estrategias;
    }


    public Pago crear(Venta venta, DetallesPago dto){
        return servicioPago.crear(venta, dto);
    }

    public Pago procesarPago(String tipoEstrategia, String pagoId, DetallesPago detalles){

        var estrategia = estrategias.get(tipoEstrategia);
        if (estrategia == null)
            throw new IllegalArgumentException("Estrategia inválida: " + tipoEstrategia);

        var resultado = estrategia.ejecutar(detalles);

        var nuevoEstado = resultado.aprobado()
                ? EstadoPago.APROBADO
                : EstadoPago.RECHAZADO;

        return servicioPago.actualizarEstado(pagoId, nuevoEstado);
    }

    @Transactional
    public PagoProcesado crearYProcesar(String idVenta, String tipoEstrategia, DetallesPago detalles){
        var venta = servicioVenta.buscarPorId(idVenta);
        var pago = crear(venta, detalles); 
        var resultado = procesarPago(tipoEstrategia, pago.getId_pago(), detalles);
        
        return new PagoProcesado(
        resultado.getId_pago(),
        idVenta,
        resultado.getMonto(),
        resultado.getMetodo_pago(),
        resultado.getEstado().toString(),
        resultado.getReferencia_transaccion(),
        "Procesado con estrategia: " + tipoEstrategia,
        "AUTH-MOCK", // o real si aplica
        resultado.getFecha_pago()
    );
    }

}
