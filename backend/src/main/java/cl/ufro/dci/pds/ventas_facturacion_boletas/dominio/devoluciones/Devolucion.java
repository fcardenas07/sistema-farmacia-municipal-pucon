package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.devoluciones;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "devolucion")
public class Devolucion {

    @Id
    @Column(name = "id_devolucion")
    private String idDevolucion;

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    @Column(name = "motivo")
    private String motivo;

    @OneToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    public Devolucion() {
    }

    public String getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(String idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Devolucion that)) return false;
        return Objects.equals(idDevolucion, that.idDevolucion);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idDevolucion);
    }

    @Override
    public String toString() {
        return "Devolucion{" +
                "idDevolucion='" + idDevolucion + '\'' +
                ", fechaDevolucion=" + fechaDevolucion +
                ", motivo='" + motivo + '\'' +
                ", venta=" + venta +
                '}';
    }
}
