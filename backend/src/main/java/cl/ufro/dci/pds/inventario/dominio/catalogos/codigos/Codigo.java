package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "codigo")
public class Codigo {

    @Id
    @Column(name = "id_codigo")
    private String idCodigo;

    @Column(name = "codigo_barra", nullable = false, unique = true)
    private String codigoBarra;

    @Column(name = "tipo_codigo", nullable = false)
    private String tipoCodigo;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    public Codigo() {}

    public Codigo(String idCodigo, String codigoBarra, String tipoCodigo, boolean activo, Producto producto) {
        this.idCodigo = idCodigo;
        this.codigoBarra = codigoBarra;
        this.tipoCodigo = tipoCodigo;
        this.activo = activo;
        this.producto = producto;
    }

    public String getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(String idCodigo) {
        this.idCodigo = idCodigo;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public String getTipoCodigo() {
        return tipoCodigo;
    }

    public void setTipoCodigo(String tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Codigo codigo = (Codigo) o;
        return activo == codigo.activo && Objects.equals(idCodigo, codigo.idCodigo) && Objects.equals(codigoBarra, codigo.codigoBarra) && Objects.equals(tipoCodigo, codigo.tipoCodigo) && Objects.equals(producto, codigo.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCodigo, codigoBarra, tipoCodigo, activo, producto);
    }

    @Override
    public String toString() {
        return "Codigo{" +
                "idCodigo=" + idCodigo +
                ", codigoBarra='" + codigoBarra + '\'' +
                ", tipoCodigo='" + tipoCodigo + '\'' +
                ", activo=" + activo +
                ", producto=" + producto +
                '}';
    }
}
