package cl.ufro.dci.pds.inventario.infraestructura;

public interface TrazabilidadIngresoProjection {
    String getIdCodigo();
    String getNombreComercial();
    String getNumeroLote();
    String getFechaElaboracion();
    String getFechaVencimiento();
    Integer getCantidad();
    String getEstado();
    String getIdMovimiento();
    String getFechaMovimiento();
    String getTipoMovimiento();
}
