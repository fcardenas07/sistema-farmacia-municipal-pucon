package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class DetalleVentaId {

    @Column(name = "id_venta")
    private String idVenta;

    @Column(name = "id_lote")
    private String idLote;

    public DetalleVentaId() {
    }

    public DetalleVentaId(String idVenta, String idLote) {
        this.idVenta = idVenta;
        this.idLote = idLote;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DetalleVentaId that)) return false;
        return Objects.equals(idVenta, that.idVenta) &&
                Objects.equals(idLote, that.idLote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVenta, idLote);
    }

    @Override
    public String toString() {
        return "DetalleVentaId{" +
                "idVenta='" + idVenta + '\'' +
                ", idLote='" + idLote + '\'' +
                '}';
    }
}
