package cl.ufro.dci.pds.inventario.dominio.control_stock.mermas;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "merma")
public class Merma {

    @Id
    @Column(name = "id_merma")
    private String idMerma;

    @Column(name = "fecha_merma")
    private LocalDate fechaMerma;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_lote", nullable = false)
    private Lote lote;

    public Merma() {
    }

    public String getIdMerma() {
        return idMerma;
    }

    public void setIdMerma(String idMerma) {
        this.idMerma = idMerma;
    }

    public LocalDate getFechaMerma() {
        return fechaMerma;
    }

    public void setFechaMerma(LocalDate fechaMerma) {
        this.fechaMerma = fechaMerma;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Merma merma)) return false;
        return Objects.equals(idMerma, merma.idMerma);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idMerma);
    }

    @Override
    public String toString() {
        return "Merma{" +
                "idMerma='" + idMerma + '\'' +
                ", fechaMerma=" + fechaMerma +
                ", detalle='" + detalle + '\'' +
                ", cantidad=" + cantidad +
                ", lote=" + lote +
                '}';
    }
}
