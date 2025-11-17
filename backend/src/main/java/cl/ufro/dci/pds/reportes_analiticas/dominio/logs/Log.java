package cl.ufro.dci.pds.reportes_analiticas.dominio.logs;

import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "log")
public class Log {

    @Id
    @Column(name = "id_log")
    private String idLog;

    @Column(name = "fecha_log")
    private LocalDate fechaLog;

    @Column(name = "tipo_cambio")
    private String tipoCambio;

    @ManyToOne
    @JoinColumn(name = "id_responsable", nullable = false)
    private Usuario responsable;

    @ManyToOne
    @JoinColumn(name = "id_afectado")
    private Usuario afectado;

    public Log() {
    }

    public String getIdLog() {
        return idLog;
    }

    public void setIdLog(String idLog) {
        this.idLog = idLog;
    }

    public LocalDate getFechaLog() {
        return fechaLog;
    }

    public void setFechaLog(LocalDate fechaLog) {
        this.fechaLog = fechaLog;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public Usuario getAfectado() {
        return afectado;
    }

    public void setAfectado(Usuario afectado) {
        this.afectado = afectado;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Log log)) return false;
        return Objects.equals(idLog, log.idLog);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idLog);
    }

    @Override
    public String toString() {
        return "Log{" +
                "idLog='" + idLog + '\'' +
                ", fechaLog=" + fechaLog +
                ", tipoCambio='" + tipoCambio + '\'' +
                ", responsable=" + responsable +
                ", afectado=" + afectado +
                '}';
    }
}
