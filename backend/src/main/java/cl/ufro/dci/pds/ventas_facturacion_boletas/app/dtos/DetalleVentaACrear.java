package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

import cl.ufro.dci.pds.compartido.eventos.ItemVenta;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.DetalleVenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record DetalleVentaACrear(
        @NotBlank(message = "La id del lote es obligatoria")
        String idLote,
        @Positive(message = "La cantidad es obligatoria")
        Integer cantidad,
        @Positive(message = "El precio unitario es obligatorio")
        Integer precioUnitario
) {

    public ItemVenta toItemVenta(){
        return new ItemVenta(
                this.idLote,
                this.cantidad,
                this.precioUnitario
        );
    }


}
