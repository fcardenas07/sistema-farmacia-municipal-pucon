package cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.pacientes.dominio.pacientes.cronicos.inscripcion.Cliente;
import cl.ufro.dci.pds.usuarios_permisos.dominio.usuarios.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_venta")
    private String idVenta;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_venta")
    private EstadoVenta estadoVenta;

    @ManyToOne
    @JoinColumn(name = "rut_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vendedor", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta() {
    }

    public void agregarDetalle(Lote lote, Integer cantidad, Integer precioUnitario) {
        var detalle = new DetalleVenta(this, lote, cantidad, precioUnitario);
        detalles.add(detalle);
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
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

    public EstadoVenta getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(EstadoVenta estadoVenta) {
        this.estadoVenta = estadoVenta;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public Integer getTotal() {
        return detalles.stream()
                .mapToInt(d -> d.getCantidad() * d.getPrecioUnitario())
                .sum();
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
                ", estadoVenta=" + estadoVenta +
                ", cliente=" + cliente +
                ", usuario=" + usuario +
                '}';
    }
}