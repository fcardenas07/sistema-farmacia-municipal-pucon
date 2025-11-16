package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @EmbeddedId
    private DetalleVentaId detalleVentaId;

    @ManyToOne
    @MapsId("idVenta")
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    @ManyToOne
    @MapsId("idLote")
    @JoinColumn(name = "id_lote", nullable = false)
    private Lote lote;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Integer precioUnitario;

    public DetalleVenta() {
    }

    public DetalleVenta(Venta venta, Lote lote, Integer cantidad, Integer precioUnitario) {
        this.detalleVentaId = new DetalleVentaId(venta.getIdVenta(), lote.getIdLote());
        this.venta = venta;
        this.lote = lote;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public DetalleVentaId getDetalleVentaId() {
        return detalleVentaId;
    }

    public void setDetalleVentaId(DetalleVentaId detalleVentaId) {
        this.detalleVentaId = detalleVentaId;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
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
        if (!(o instanceof DetalleVenta that)) return false;
        return Objects.equals(detalleVentaId, that.detalleVentaId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(detalleVentaId);
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "detalleVentaId=" + detalleVentaId +
                ", venta=" + venta +
                ", lote=" + lote +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                '}';
    }
}
