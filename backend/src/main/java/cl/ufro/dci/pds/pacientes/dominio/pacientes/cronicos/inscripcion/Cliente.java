package cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion;

import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(name = "rut_cliente")
    private String rutCliente;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "email")
    private String email;

    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "medico_prescriptor")
    private String medicoPrescriptor;

    @Column(name = "nombre_tutor")
    private String nombreTutor;

    @Column(name = "rut_tutor")
    private String rutTutor;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public Cliente() {
    }

    public String getRutCliente() {
        return rutCliente;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMedicoPrescriptor() {
        return medicoPrescriptor;
    }

    public void setMedicoPrescriptor(String medicoPrescriptor) {
        this.medicoPrescriptor = medicoPrescriptor;
    }

    public String getNombreTutor() {
        return nombreTutor;
    }

    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;
    }

    public String getRutTutor() {
        return rutTutor;
    }

    public void setRutTutor(String rutTutor) {
        this.rutTutor = rutTutor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cliente cliente)) return false;
        return Objects.equals(rutCliente, cliente.rutCliente);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rutCliente);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "rutCliente='" + rutCliente + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", email='" + email + '\'' +
                ", fechaInscripcion=" + fechaInscripcion +
                ", estado='" + estado + '\'' +
                ", medicoPrescriptor='" + medicoPrescriptor + '\'' +
                ", nombreTutor='" + nombreTutor + '\'' +
                ", rutTutor='" + rutTutor + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
