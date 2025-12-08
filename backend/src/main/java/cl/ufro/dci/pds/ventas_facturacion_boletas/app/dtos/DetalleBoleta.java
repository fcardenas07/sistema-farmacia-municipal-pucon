package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record DetalleBoleta(
        String idBoleta,
        Integer numero,
        LocalDateTime fechaEmision,
        Integer montoTotal,
        String nombreCliente,
        String rutCliente,
        String nombreVendedor,
        List<ItemBoleta> items
) {
}
