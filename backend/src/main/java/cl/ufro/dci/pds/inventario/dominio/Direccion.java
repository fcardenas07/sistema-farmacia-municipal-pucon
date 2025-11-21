package cl.ufro.dci.pds.inventario.dominio;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Direccion {

    private String calle;
    private String numero;
    private String ciudad;
    private String pais;

    public Direccion() {
    }

    public Direccion(String calle, String numero, String ciudad, String pais) {
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return Objects.equals(calle, direccion.calle) && Objects.equals(numero, direccion.numero) && Objects.equals(ciudad, direccion.ciudad) && Objects.equals(pais, direccion.pais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calle, numero, ciudad, pais);
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
