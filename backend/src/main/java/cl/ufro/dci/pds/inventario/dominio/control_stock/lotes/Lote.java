package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import cl.ufro.dci.pds.inventario.dominio.abastecimiento.guiasingreso.GuiaIngreso;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "lote")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_lote")
    private String idLote;

    @Column(name = "fecha_elaboracion", nullable = false)
    private LocalDate fechaElaboracion;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "numero_lote", nullable = false)
    private String numeroLote;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "precio_unitario", nullable = false)
    private Integer precioUnitario;

    @Column(name = "limite_merma")
    private Integer limiteMerma;

    @Column(name = "porcentaje_oferta")
    private Float porcentajeOferta;

    @Column(name = "stock_inicial")
    private Integer stockInicial;

    @Column(name = "stock_actual")
    private Integer stockActual = 0;

    @Column(name = "stock_reservado")
    private Integer stockReservado = 0;

    @ManyToOne
    @JoinColumn(name = "id_codigo", nullable = false)
    private Codigo codigo;

    @ManyToOne
    @JoinColumn(name = "id_guia_ingreso", nullable = true) //por ahora que no está implementado guiaingreso
    private GuiaIngreso  guiaIngreso;

    public Lote() {
    }

    public String getIdLote() {
        return idLote;
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

    public Integer getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Integer precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getLimiteMerma() {
        return limiteMerma;
    }

    public void setLimiteMerma(Integer limiteMerma) {
        this.limiteMerma = limiteMerma;
    }

    public Float getPorcentajeOferta() {
        return porcentajeOferta;
    }

    public void setPorcentajeOferta(Float porcentajeOferta) {
        this.porcentajeOferta = porcentajeOferta;
    }

    public Integer getStockInicial() {
        return stockInicial;
    }

    public void setStockInicial(Integer stockInicial) {
        this.stockInicial = stockInicial;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Integer getStockReservado() {
        return stockReservado;
    }

    public void setStockReservado(Integer stockReservado) {
        this.stockReservado = stockReservado;
    }

    public Integer getStockDisponible() {
        return stockActual - stockReservado;
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }

    public GuiaIngreso getGuiaIngreso() {
        return guiaIngreso;
    }

    public void setGuiaIngreso(GuiaIngreso guiaIngreso) {
        this.guiaIngreso = guiaIngreso;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Lote lote)) return false;
        return Objects.equals(idLote, lote.idLote);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idLote);
    }

    @Override
    public String toString() {
        return "Lote{" +
                "idLote='" + idLote + '\'' +
                ", fechaElaboracion=" + fechaElaboracion +
                ", fechaVencimiento=" + fechaVencimiento +
                ", numeroLote='" + numeroLote + '\'' +
                ", estado='" + estado + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", limiteMerma=" + limiteMerma +
                ", porcentajeOferta=" + porcentajeOferta +
                ", stockInicial=" + stockInicial +
                ", stockActual=" + stockActual +
                ", stockReservado=" + stockReservado +
                ", codigo=" + codigo +
                ", guiaIngreso=" + guiaIngreso +
                '}';
    }
}
