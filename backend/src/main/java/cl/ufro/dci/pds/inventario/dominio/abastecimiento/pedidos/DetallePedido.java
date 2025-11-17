package cl.ufro.dci.pds.inventario.dominio.abastecimiento.pedidos;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @EmbeddedId
    private DetallePedidoId detallePedidoId;

    @ManyToOne
    @MapsId("idPedido")
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @MapsId("idLote")
    @JoinColumn(name = "id_lote", nullable = false)
    private Lote lote;


    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Integer precioUnitario;

    public DetallePedido() {
    }

    public DetallePedido(Pedido pedido, Lote lote, Integer cantidad, Integer precioUnitario) {
        this.detallePedidoId = new DetallePedidoId(pedido.getIdPedido(), lote.getIdLote());
        this.pedido = pedido;
        this.lote = lote;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }


    public DetallePedidoId getDetallePedidoId() {
        return detallePedidoId;
    }

    public void setDetallePedidoId(DetallePedidoId detallePedidoId) {
        this.detallePedidoId = detallePedidoId;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Integer precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetallePedido that)) return false;
        return Objects.equals(detallePedidoId, that.detallePedidoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(detallePedidoId);
    }


    @Override
    public String toString() {
        return "DetallePedido{" +
                "detallePedidoId=" + detallePedidoId +
                ", pedido=" + pedido +
                ", lote=" + lote +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                '}';
    }
}
