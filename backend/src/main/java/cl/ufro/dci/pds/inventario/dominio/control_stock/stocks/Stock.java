package cl.ufro.dci.pds.inventario.dominio.control_stock.stocks;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @Column(name = "id_stock")
    private String idStock;

    @Column(name = "cantidad_inicial", nullable = false)
    private int cantidadInicial;

    @Column(name = "cantidad_actual", nullable = false)
    private int cantidadActual;

    @Column(name = "precio", nullable = false)
    private double precio;

    @OneToOne
    @JoinColumn(name = "id_lote", nullable = false, unique = true)
    private Lote lote;

    public Stock() {}

    public String getIdStock() {
        return idStock;
    }

    public void setIdStock(String idStock) {
        this.idStock = idStock;
    }

    public int getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(int cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stock)) return false;
        Stock stock = (Stock) o;
        return Objects.equals(idStock, stock.idStock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStock);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "idStock='" + idStock + '\'' +
                ", cantidadInicial=" + cantidadInicial +
                ", cantidadActual=" + cantidadActual +
                ", precio=" + precio +
                ", lote=" + (lote != null ? lote.getNumeroLote() : null) +
                '}';
    }
}
