package cl.ufro.dci.pds.inventario.infraestructura;

public interface TrazabilidadProjection {
    String getIdCodigo();
    String getNombreComercial();
    String getCodigoLote();
    String getFechaElaboracion();
    String getFechaVencimiento();
    Integer getCantidad();
    String getEstado();
    String getUltimoMovimiento();
    String getTipoMovimiento();
}
