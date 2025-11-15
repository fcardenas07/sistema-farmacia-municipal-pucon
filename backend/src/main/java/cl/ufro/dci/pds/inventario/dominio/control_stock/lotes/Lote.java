package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "lote")
public class Lote {

    @Id
    @Column(name = "id_lote")
    private String idLote;

    @Column(name = "fecha_elaboracion", nullable = false)
    private LocalDate fechaElaboracion;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "numero_lote", nullable = false, unique = true)
    private String numeroLote;

    @Column(name = "estado", nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @OneToOne(mappedBy = "lote", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public LocalDate getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(LocalDate fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(String numeroLote) {
        this.numeroLote = numeroLote;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
        if (stock != null) stock.setLote(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lote lote = (Lote) o;
        return Objects.equals(idLote, lote.idLote) && Objects.equals(fechaElaboracion, lote.fechaElaboracion) && Objects.equals(fechaVencimiento, lote.fechaVencimiento) && Objects.equals(numeroLote, lote.numeroLote) && Objects.equals(estado, lote.estado) && Objects.equals(producto, lote.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLote, fechaElaboracion, fechaVencimiento, numeroLote, estado, producto);
    }

    @Override
    public String toString() {
        return "Lote{" +
                "idLote='" + idLote + '\'' +
                ", fechaElaboracion=" + fechaElaboracion +
                ", fechaVencimiento=" + fechaVencimiento +
                ", numeroLote='" + numeroLote + '\'' +
                ", estado='" + estado + '\'' +
                ", producto=" + producto + '\'' +
                ", stock=" + (stock != null ? stock.getCantidadActual() : "null") +
                '}';
    }
}
