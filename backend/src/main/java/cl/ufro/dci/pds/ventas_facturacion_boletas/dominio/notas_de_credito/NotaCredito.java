package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.notas_de_credito;

import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.Cliente;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.devoluciones.Devolucion;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.facturacion.boletas.Boleta;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "nota_credito")
public class NotaCredito {

    @Id
    @Column(name = "id_notacredito")
    private String idNotaCredito;

    @Column(name = "numero_nota")
    private Integer numeroNota;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "monto")
    private Integer monto;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "sii_folio")
    private String siiFolio;

    @Column(name = "estado")
    private String estado;

    @OneToOne
    @JoinColumn(name = "id_boleta", nullable = false)
    private Boleta boleta;

    @OneToOne
    @JoinColumn(name = "id_devolucion", nullable = false)
    private Devolucion devolucion;

    public NotaCredito() {
    }

    public String getIdNotaCredito() {
        return idNotaCredito;
    }

    public void setIdNotaCredito(String idNotaCredito) {
        this.idNotaCredito = idNotaCredito;
    }

    public Integer getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(Integer numeroNota) {
        this.numeroNota = numeroNota;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getSiiFolio() {
        return siiFolio;
    }

    public void setSiiFolio(String siiFolio) {
        this.siiFolio = siiFolio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boleta getBoleta() {
        return boleta;
    }

    public void setBoleta(Boleta boleta) {
        this.boleta = boleta;
    }

    public Devolucion getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NotaCredito that)) return false;
        return Objects.equals(idNotaCredito, that.idNotaCredito);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idNotaCredito);
    }

    @Override
    public String toString() {
        return "NotaCredito{" +
                "idNotaCredito='" + idNotaCredito + '\'' +
                ", numeroNota=" + numeroNota +
                ", fechaEmision=" + fechaEmision +
                ", monto=" + monto +
                ", motivo='" + motivo + '\'' +
                ", siiFolio='" + siiFolio + '\'' +
                ", estado='" + estado + '\'' +
                ", boleta=" + boleta +
                ", devolucion=" + devolucion +
                '}';
    }
}
