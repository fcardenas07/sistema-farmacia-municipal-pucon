package cl.ufro.dci.pds.inventario.infraestructura;

public interface TrazabilidadIngresoProjection {
    String getIdCodigo();
    String getNombreComercial();
    String getNumeroLote();
    String getFechaElaboracion();
    String getFechaVencimiento();
    Integer getCantidad();
    String getEstado();
    String getFechaMovimiento();
    String getTipoMovimiento();
}
