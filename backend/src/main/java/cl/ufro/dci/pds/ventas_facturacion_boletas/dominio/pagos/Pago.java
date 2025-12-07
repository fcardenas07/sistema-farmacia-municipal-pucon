package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.pagos;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_pago")
    private String id_pago;

    @Column(name = "metodo_pago")
    private String metodo_pago;

    @Column(name = "monto")
    private Integer monto;

    @Column(name = "fecha_pago")
    private LocalDateTime fecha_pago;

    @Column(name = "referencia_transaccion")
    private String referencia_transaccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPago estado;

    @OneToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    public Pago() {
    }

    public String getId_pago() {
        return id_pago;
    }

    public void setId_pago(String id_pago) {
        this.id_pago = id_pago;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(LocalDateTime fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public String getReferencia_transaccion() {
        return referencia_transaccion;
    }

    public void setReferencia_transaccion(String referencia_transaccion) {
        this.referencia_transaccion = referencia_transaccion;
    }



    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pago pago)) return false;
        return Objects.equals(id_pago, pago.id_pago);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_pago);
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id_pago='" + id_pago + '\'' +
                ", metodo_pago='" + metodo_pago + '\'' +
                ", monto=" + monto +
                ", fecha_pago=" + fecha_pago +
                ", referencia_transaccion='" + referencia_transaccion + '\'' +
                ", estado='" + estado + '\'' +
                ", venta=" + venta +
                '}';
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }
}
