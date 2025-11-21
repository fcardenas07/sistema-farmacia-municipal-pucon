package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.fabricantes.Fabricante;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_producto")
    private String idProducto;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Column(name = "nombre_generico")
    private String nombreGenerico;

    @Column(name = "presentacion")
    private String presentacion;

    @Column(name = "dosificacion")
    private int dosificacion;

    @Column(name = "unidad_medida")
    private String unidadMedida;

    @Column(name = "stock_minimo")
    private int stockMinimo;

    @Column(name = "stockMaximo")
    private int stockMaximo;

    @Column(name = "activo")
    private boolean activo;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaProducto categoria;

    @Column(name = "url_foto")
    private String urlFoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fabricante")
    private Fabricante fabricante;

    public Producto() {
    }

    public Producto(String nombreComercial, String nombreGenerico, String presentacion,
                    int dosificacion, String unidadMedida, int stockMinimo, int stockMaximo,
                    boolean activo, CategoriaProducto categoria, String urlFoto) {
        this.nombreComercial = nombreComercial;
        this.nombreGenerico = nombreGenerico;
        this.presentacion = presentacion;
        this.dosificacion = dosificacion;
        this.unidadMedida = unidadMedida;
        this.stockMinimo = stockMinimo;
        this.stockMaximo = stockMaximo;
        this.activo = activo;
        this.categoria = categoria;
        this.urlFoto = urlFoto;
    }

    public String getIdProducto() {
        return idProducto;
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

    public int getDosificacion() {
        return dosificacion;
    }

    public void setDosificacion(int dosificacion) {
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getCategoria() {
        return categoria.getNombreLegible();
    }

    public CategoriaProducto getCategoriaProducto() {
        return categoria;
    }

    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Producto producto)) return false;
        return Objects.equals(idProducto, producto.idProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idProducto);
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto='" + idProducto + '\'' +
                ", nombreComercial='" + nombreComercial + '\'' +
                ", nombreGenerico='" + nombreGenerico + '\'' +
                ", presentacion='" + presentacion + '\'' +
                ", dosificacion='" + dosificacion + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", stockMinimo=" + stockMinimo + '\'' +
                ", stockMaximo=" + stockMaximo + '\'' +
                ", activo='" + activo + '\'' +
                ", categoría='" + categoria + '\'' +
                ", url foto=" + urlFoto + '\'' +
                ", fabricante=" + fabricante + '}';
    }
}
