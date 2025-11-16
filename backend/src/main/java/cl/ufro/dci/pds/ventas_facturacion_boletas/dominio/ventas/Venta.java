package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.Cliente;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @Column(name = "id_venta")
    private String idVenta;

    @Column(name = "fecha_venta")
    private LocalDate fechaVenta;

    @Column(name = "total")
    private Integer total;

    @ManyToOne
    @JoinColumn(name = "rut_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vendedor", nullable = false)
    private Usuario usuario;

    public Venta() {
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Venta venta)) return false;
        return Objects.equals(idVenta, venta.idVenta);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idVenta);
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta='" + idVenta + '\'' +
                ", fechaVenta=" + fechaVenta +
                ", total=" + total +
                ", cliente=" + cliente +
                ", usuario=" + usuario +
                '}';
    }
}
