package cl.ufro.dci.pds.pacientes.dominio.pacientes.documentos;

import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.Cliente;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "documentacion")
public class Documentacion {

    @Id
    @Column(name = "id_documentacion")
    private String idDocumentacion;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @ManyToOne
    @JoinColumn(name = "rut_cliente")
    private Cliente cliente;

    public Documentacion() {
    }

    public String getIdDocumentacion() {
        return idDocumentacion;
    }

    public void setIdDocumentacion(String idDocumentacion) {
        this.idDocumentacion = idDocumentacion;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Cliente getRutCliente() {
        return cliente;
    }

    public void setRutCliente(Cliente rutCliente) {
        this.cliente = rutCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Documentacion that)) return false;
        return Objects.equals(idDocumentacion, that.idDocumentacion);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idDocumentacion);
    }

    @Override
    public String toString() {
        return "Documentacion{" +
                "idDocumentacion='" + idDocumentacion + '\'' +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", cliente=" + cliente +
                '}';
    }
}
