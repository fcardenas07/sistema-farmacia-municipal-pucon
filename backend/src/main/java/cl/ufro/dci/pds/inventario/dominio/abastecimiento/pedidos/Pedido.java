package cl.ufro.dci.pds.inventario.dominio.abastecimiento.pedidos;

import cl.ufro.dci.pds.inventario.dominio.abastecimiento.proveedores.Proveedor;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @Column(name = "id_pedido")
    private String idPedido;

    @Column(name = "fecha_pedido")
    private LocalDate fechaPedido;

    @Column(name = "estado")
    private boolean estado;

    @Column(name = "total")
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "rut_proveedor", nullable = false)
    private Proveedor proveedor;

    public Pedido() {
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pedido pedido)) return false;
        return Objects.equals(idPedido, pedido.idPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idPedido);
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido='" + idPedido + '\'' +
                ", fechaPedido=" + fechaPedido +
                ", estado=" + estado +
                ", total=" + total +
                ", proveedor=" + proveedor +
                '}';
    }
}
