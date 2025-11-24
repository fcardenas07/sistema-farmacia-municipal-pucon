package cl.ufro.dci.pds.inventario.dominio.abastecimiento.guiasingreso;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "detalle_guia_ingreso")
public class DetalleGuiaIngreso {

    @EmbeddedId
    private DetalleGuiaIngresoId id;

    @ManyToOne
    @MapsId("idProducto")
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne
    @MapsId("idGuiaIngreso")
    @JoinColumn(name = "id_guia_ingreso", nullable = false)
    private GuiaIngreso guiaIngreso;

    @Column(name = "cantidad")
    private Integer cantidad;

    public DetalleGuiaIngreso() {
    }

    public DetalleGuiaIngresoId getId() {
        return id;
    }

    public void setId(DetalleGuiaIngresoId idGuiaIngreso) {
        this.id = idGuiaIngreso;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public GuiaIngreso getGuiaIngreso() {
        return guiaIngreso;
    }

    public void setGuiaIngreso(GuiaIngreso guiaIngreso) {
        this.guiaIngreso = guiaIngreso;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DetalleGuiaIngreso that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DetalleGuiaIngreso{" +
                "idGuiaIngreso=" + id +
                ", producto=" + producto +
                ", guiaIngreso=" + guiaIngreso +
                ", cantidad=" + cantidad +
                '}';
    }
}
