package cl.ufro.dci.pds.inventario.dominio;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.util.Objects;

@Embeddable
public class DatosContacto {

    private String identificador;
    private String nombre;
    private String telefonoContacto;
    private String correoElectronico;

    @Embedded
    private Direccion direccion;

    public DatosContacto() {
    }

    public DatosContacto(String identificador, String nombre, String telefonoContacto, String correoElectronico, Direccion direccion) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.telefonoContacto = telefonoContacto;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatosContacto that)) return false;
        return Objects.equals(identificador, that.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return "DatosContacto{" +
                "identificador='" + identificador + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefonoContacto='" + telefonoContacto + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", direccion=" + direccion +
                '}';
    }
}
