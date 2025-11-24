package cl.ufro.dci.pds.inventario.dominio.abastecimiento.guiasingreso;

import cl.ufro.dci.pds.inventario.dominio.abastecimiento.proveedores.Proveedor;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "guia_ingreso")
public class GuiaIngreso {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    @Column(name = "id_guia_ingreso")
    private String idGuiaIngreso;

    @Column(name = "factura")
    private String factura;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "rut_proveedor")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "guiaIngreso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleGuiaIngreso> detallesGuiaIngreso = new ArrayList<>();

    public GuiaIngreso() {
    }

    public void addDetalle(DetalleGuiaIngreso detalle) {
        detalle.setGuiaIngreso(this);
        this.detallesGuiaIngreso.add(detalle);
    }

    public void removeDetalle(DetalleGuiaIngreso detalle) {
        detalle.setGuiaIngreso(null);
        this.detallesGuiaIngreso.remove(detalle);
    }


    public String getIdGuiaIngreso() {
        return idGuiaIngreso;
    }

    public void setIdGuiaIngreso(String idGuiaIngreso) {
        this.idGuiaIngreso = idGuiaIngreso;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<DetalleGuiaIngreso> getDetallesGuiaIngreso() {
        return detallesGuiaIngreso;
    }

    public void setDetallesGuiaIngreso(List<DetalleGuiaIngreso> detallesGuiaIngreso) {
        this.detallesGuiaIngreso = detallesGuiaIngreso;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GuiaIngreso that)) return false;
        return Objects.equals(idGuiaIngreso, that.idGuiaIngreso);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idGuiaIngreso);
    }

    @Override
    public String toString() {
        return "GuiaIngreso{" +
                "idGuiaIngreso='" + idGuiaIngreso + '\'' +
                ", factura='" + factura + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", observaciones='" + observaciones + '\'' +
                ", proveedor=" + proveedor +
                ", detallesGuiaIngreso=" + detallesGuiaIngreso +
                '}';
    }
}
