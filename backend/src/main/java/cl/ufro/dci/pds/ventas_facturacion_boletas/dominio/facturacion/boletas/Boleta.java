package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.facturacion.boletas;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "boleta")
public class Boleta {

    @Id
    @Column(name = "id_boleta")
    private String idBoleta;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "monto_total")
    private Integer montoTotal;

    @OneToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    public Boleta() {
    }

    public String getIdBoleta() {
        return idBoleta;
    }

    public void setIdBoleta(String idBoleta) {
        this.idBoleta = idBoleta;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Integer getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Boleta boleta)) return false;
        return Objects.equals(idBoleta, boleta.idBoleta);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idBoleta);
    }

    @Override
    public String toString() {
        return "Boleta{" +
                "idBoleta='" + idBoleta + '\'' +
                ", numero=" + numero +
                ", fechaEmision=" + fechaEmision +
                ", montoTotal=" + montoTotal +
                ", venta=" + venta +
                '}';
    }
}
