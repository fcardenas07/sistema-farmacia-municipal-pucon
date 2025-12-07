package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.facturacion.boletas;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "boleta")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_boleta")
    private String idBoleta;

    @Column(
            name = "numero_boleta",
            unique = true,
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Long numeroBoleta;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

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

    public Long getNumeroBoleta() {
        return numeroBoleta;
    }

    public void setNumeroBoleta(Long numeroBoleta) {
        this.numeroBoleta = numeroBoleta;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
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
                ", numeroBoleta=" + numeroBoleta +
                ", fechaEmision=" + fechaEmision +
                ", montoTotal=" + montoTotal +
                ", venta=" + venta +
                '}';
    }
}
