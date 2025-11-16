package cl.ufro.dci.pds.pedidos_domicilio.dominio.pedidos_a_domicilio;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "despacho")
public class Despacho {

    @Id
    @Column(name = "id_despacho")
    private String idDespacho;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @Column(name = "estado")
    private String estado;

    @Column(name = "direccion_entrega")
    private String direccionEntrega;

    @OneToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    public Despacho() {
    }

    public String getIdDespacho() {
        return idDespacho;
    }

    public void setIdDespacho(String idDespacho) {
        this.idDespacho = idDespacho;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Despacho despacho)) return false;
        return Objects.equals(idDespacho, despacho.idDespacho);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idDespacho);
    }

    @Override
    public String toString() {
        return "Despacho{" +
                "idDespacho='" + idDespacho + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                ", estado='" + estado + '\'' +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", venta=" + venta +
                '}';
    }
}
