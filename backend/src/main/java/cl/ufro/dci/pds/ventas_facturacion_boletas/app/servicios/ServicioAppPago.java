package cl.ufro.dci.pds.ventas_facturacion_boletas.app.servicios;

import java.util.Map;

import cl.ufro.dci.pds.compartido.eventos.EventoResultadoPago;
import cl.ufro.dci.pds.infraestructura.BusEventosVentas;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos.*;
import org.springframework.stereotype.Service;

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
    private final BusEventosVentas busEventosVentas;

    ServicioAppPago(ServicioPago servicioPago,
                    ServicioVenta servicioVenta,
                    Map<String, PagoEstrategia> estrategias,
                    BusEventosVentas busEventosVentas) {
        this.servicioPago = servicioPago;
        this.servicioVenta = servicioVenta;
        this.estrategias = estrategias;
        this.busEventosVentas = busEventosVentas;
    }

    @Transactional
    public PagoProcesado crearYProcesar(String idVenta, String tipoEstrategia, DetallesPago detalles) {
        var venta = servicioVenta.buscarPorId(idVenta);
        var pago = crear(venta, detalles);
        var resultado = procesarPago(tipoEstrategia, pago.getId_pago(), detalles);

        publicarResultadoPago(venta, pago.getEstado());

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

    public Pago crear(Venta venta, DetallesPago dto) {
        return servicioPago.crear(venta, dto);
    }

    public Pago procesarPago(String tipoEstrategia, String pagoId, DetallesPago detalles) {
        var estrategia = estrategias.get(tipoEstrategia);
        if (estrategia == null) {
            throw new EstrategiaPagoNoEncontradaException(tipoEstrategia);
        }

        var resultado = estrategia.ejecutar(detalles);

        var nuevoEstado = resultado.aprobado()
                ? EstadoPago.APROBADO
                : EstadoPago.RECHAZADO;

        return servicioPago.actualizarEstado(pagoId, nuevoEstado);
    }

    private void publicarResultadoPago(Venta venta, EstadoPago estadoPago) {
        busEventosVentas.emitirResultadoPago(new EventoResultadoPago(venta, estadoPago));
    }
}
