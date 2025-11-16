package cl.ufro.dci.pds.inventario.dominio.control_stock.fraccionamientos;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.Cliente;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "fraccionamiento")
public class Fraccionamiento {

    @Id
    @Column(name = "id_fraccionamiento")
    private String idFraccionamiento;

    @Column(name = "cantidad_fraccionada")
    private Integer cantidadFraccionada;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "posologia")
    private String posologia;

    @Column(name = "fecha_fraccionamiento")
    private LocalDate fechaFraccionamiento;

    @Column(name = "etiqueta_generada")
    private String etiquetaGenerada;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_lote", nullable = false)
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "rut_cliente", nullable = false)
    private Cliente cliente;

    public Fraccionamiento() {
    }

    public String getIdFraccionamiento() {
        return idFraccionamiento;
    }

    public void setIdFraccionamiento(String idFraccionamiento) {
        this.idFraccionamiento = idFraccionamiento;
    }

    public Integer getCantidadFraccionada() {
        return cantidadFraccionada;
    }

    public void setCantidadFraccionada(Integer cantidadFraccionada) {
        this.cantidadFraccionada = cantidadFraccionada;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public LocalDate getFechaFraccionamiento() {
        return fechaFraccionamiento;
    }

    public void setFechaFraccionamiento(LocalDate fechaFraccionamiento) {
        this.fechaFraccionamiento = fechaFraccionamiento;
    }

    public String getEtiquetaGenerada() {
        return etiquetaGenerada;
    }

    public void setEtiquetaGenerada(String etiquetaGenerada) {
        this.etiquetaGenerada = etiquetaGenerada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Cliente getRutCliente() {
        return cliente;
    }

    public void setRutCliente(Cliente rutCliente) {
        this.cliente = rutCliente;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Fraccionamiento that)) return false;
        return Objects.equals(idFraccionamiento, that.idFraccionamiento);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idFraccionamiento);
    }

    @Override
    public String toString() {
        return "Fraccionamiento{" +
                "idFraccionamiento='" + idFraccionamiento + '\'' +
                ", cantidadFraccionada=" + cantidadFraccionada +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", posologia='" + posologia + '\'' +
                ", fechaFraccionamiento=" + fechaFraccionamiento +
                ", etiquetaGenerada='" + etiquetaGenerada + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", usuario=" + usuario +
                ", lote=" + lote +
                ", cliente=" + cliente +
                '}';
    }
}
