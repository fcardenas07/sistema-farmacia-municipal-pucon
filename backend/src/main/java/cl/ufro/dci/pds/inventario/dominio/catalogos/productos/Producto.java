package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @Column(name = "id_producto")
    private String idProducto;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Column(name = "nombre_generico")
    private String nombreGenerico;

    @Column(name = "presentacion")
    private String presentacion;

    @Column(name = "dosificacion")
    private String dosificacion;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "stock_minimo")
    private int stockMinimo;

    @Column(name = "stockMaximo")
    private int stockMaximo;

    @Column(name = "estado")
    private String estado;

    public Producto() {
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDosificacion() {
        return dosificacion;
    }

    public void setDosificacion(String dosificacion) {
        this.dosificacion = dosificacion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(int stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(idProducto, producto.idProducto) && Objects.equals(nombreComercial, producto.nombreComercial) && Objects.equals(nombreGenerico, producto.nombreGenerico) && Objects.equals(presentacion, producto.presentacion) && Objects.equals(dosificacion, producto.dosificacion) && Objects.equals(unidadMedida, producto.unidadMedida) && Objects.equals(stockMinimo, producto.stockMinimo) && Objects.equals(stockMaximo, producto.stockMaximo) && Objects.equals(estado, producto.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, nombreComercial, nombreGenerico, presentacion, dosificacion, unidadMedida, stockMinimo, stockMaximo, estado);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto='" + idProducto + '\'' +
                ", nombreComercial='" + nombreComercial + '\'' +
                ", nombreGenerico='" + nombreGenerico + '\'' +
                ", presentacion='" + presentacion + '\'' +
                ", dosificacion='" + dosificacion + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", stockMinimo=" + stockMinimo +
                ", stockMaximo=" + stockMaximo +
                ", estado='" + estado +
                '}';
    }
}
