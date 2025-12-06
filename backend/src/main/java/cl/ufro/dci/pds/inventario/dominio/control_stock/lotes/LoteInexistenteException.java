package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

public class LoteInexistenteException extends RuntimeException {
    public LoteInexistenteException(String idLote) {
        super(String.format("No se encontró el lote con ID '%s'.", idLote));
    }}
