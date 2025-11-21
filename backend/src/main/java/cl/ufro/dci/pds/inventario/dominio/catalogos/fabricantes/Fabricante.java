package cl.ufro.dci.pds.inventario.dominio.catalogos.fabricantes;

import cl.ufro.dci.pds.inventario.dominio.DatosContacto;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "fabricante")
public class Fabricante {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_fabricante")
    private String idFabricante;

    @Embedded
    private DatosContacto contacto;

    public Fabricante() {
    }

    public Fabricante(DatosContacto contacto) {
        this.contacto = contacto;
    }

    public String getIdFabricante() {
        return idFabricante;
    }

    public DatosContacto getContacto() {
        return contacto;
    }

    public void setContacto(DatosContacto contacto) {
        this.contacto = contacto;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Fabricante fabricante)) return false;
        return Objects.equals(idFabricante, fabricante.idFabricante);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idFabricante);
    }

    @Override
    public String toString() {
        return "Producto{" + "idFabricante='" + idFabricante + '\'' +
                ", contacto=" + contacto + '}';
    }
}