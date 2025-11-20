package cl.ufro.dci.pds.inventario.dominio.abastecimiento.guiasingreso;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class DetalleGuiaIngresoId {

    @Column(name = "id_guia_ingreso")
    private String idGuiaIngreso;

    @Column(name = "id_producto")
    private String idProducto;

    public DetalleGuiaIngresoId() {}

    public DetalleGuiaIngresoId(String idGuiaingreso, String idProducto) {
        this.idGuiaIngreso = idGuiaingreso;
        this.idProducto = idProducto;
    }

    public String getIdGuiaIngreso() {
        return idGuiaIngreso;
    }

    public void setIdGuiaIngreso(String idGuiaingreso) {
        this.idGuiaIngreso = idGuiaingreso;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DetalleGuiaIngresoId that)) return false;
        return Objects.equals(idGuiaIngreso, that.idGuiaIngreso) && Objects.equals(idProducto, that.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGuiaIngreso, idProducto);
    }

    @Override
    public String toString() {
        return "DetalleGuiaIngresoId{" +
                "idGuiaIngreso='" + idGuiaIngreso + '\'' +
                ", idProducto='" + idProducto + '\'' +
                '}';
    }
}
