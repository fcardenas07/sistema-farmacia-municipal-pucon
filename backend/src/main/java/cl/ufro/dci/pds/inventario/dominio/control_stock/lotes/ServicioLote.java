package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioLote {
    private final RepositorioLote repositorioLote;

    public ServicioLote(RepositorioLote repositorioLote) {
        this.repositorioLote = repositorioLote;
    }

    public List<Lote> obtenerLotesDeCodigos(List<String> idsCodigo) {
        if (idsCodigo == null || idsCodigo.isEmpty()) {
            return List.of();
        }
        return repositorioLote.findByCodigo_IdCodigoIn(idsCodigo);
    }

    public void darBaja(Lote lote) {
        lote.setEstado("INACTIVO");
        repositorioLote.save(lote);
    }
}