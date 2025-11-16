package cl.ufro.dci.pds.inventario.dominio.abastecimiento.pedidos;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class DetallePedidoId {

    @Column(name = "id_pedido")
    private String idPedido;

    @Column(name = "id_lote")
    private String idLote;

    public  DetallePedidoId() {
    }
    public DetallePedidoId(String idPedido, String idLote) {
        this.idPedido = idPedido;
        this.idLote = idLote;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DetallePedidoId that)) return false;
        return Objects.equals(idPedido, that.idPedido) && Objects.equals(idLote, that.idLote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idLote);
    }

    @Override
    public String toString() {
        return "DetallePedidoId{" +
                "idPedido='" + idPedido + '\'' +
                ", idLote='" + idLote + '\'' +
                '}';
    }
}
