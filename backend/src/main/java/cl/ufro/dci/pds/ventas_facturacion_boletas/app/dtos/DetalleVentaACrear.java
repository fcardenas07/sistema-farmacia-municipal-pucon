package cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos;

import cl.ufro.dci.pds.compartido.eventos.ItemVenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record DetalleVentaACrear(
        @NotBlank(message = "La id del producto es obligatoria")
        String idProducto,
        @NotBlank(message = "La id del lote es obligatoria")
        String idLote,
        @Positive(message = "La cantidad es obligatoria")
        Integer cantidad,
        @Positive(message = "El precio unitario es obligatorio")
        Integer precioUnitario
) {

    public ItemVenta toItemVenta(){
        return new ItemVenta(
                this.idProducto,
                this.idLote,
                this.cantidad
        );
    }
}
