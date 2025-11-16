package cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos;

import cl.ufro.dci.pds.inventario.dominio.abastecimiento.pedidos.Pedido;
import cl.ufro.dci.pds.inventario.dominio.control_stock.fraccionamientos.Fraccionamiento;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.mermas.Merma;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.devoluciones.Devolucion;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @Column(name = "id_movimiento")
    private String idMovimiento;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "fecha_movimiento")
    private LocalDate fechaMovimiento;

    @Column(name = "detalle")
    private String detalle;

    @ManyToOne
    @JoinColumn(name = "id_lote", nullable = false)
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "id_devolucion")
    private Devolucion devolucion;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_fraccionamiento")
    private Fraccionamiento fraccionamiento;

    @ManyToOne
    @JoinColumn(name = "id_merma")
    private Merma merma;

    public Movimiento() {
    }

    public String getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(String idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Devolucion getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Fraccionamiento getFraccionamiento() {
        return fraccionamiento;
    }

    public void setFraccionamiento(Fraccionamiento fraccionamiento) {
        this.fraccionamiento = fraccionamiento;
    }

    public Merma getMerma() {
        return merma;
    }

    public void setMerma(Merma merma) {
        this.merma = merma;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movimiento that)) return false;
        return Objects.equals(idMovimiento, that.idMovimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idMovimiento);
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "idMovimiento='" + idMovimiento + '\'' +
                ", tipoMovimiento='" + tipoMovimiento + '\'' +
                ", cantidad=" + cantidad +
                ", fechaMovimiento=" + fechaMovimiento +
                ", detalle='" + detalle + '\'' +
                ", lote=" + lote +
                ", venta=" + venta +
                ", devolucion=" + devolucion +
                ", pedido=" + pedido +
                ", fraccionamiento=" + fraccionamiento +
                ", merma=" + merma +
                '}';
    }
}
