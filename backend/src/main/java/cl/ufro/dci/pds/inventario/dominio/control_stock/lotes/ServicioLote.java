package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioLote {
    private final RepositorioLote repositorioLote;

    public ServicioLote(RepositorioLote repositorioLote) {
        this.repositorioLote = repositorioLote;
    }

    public List<Lote> obtenerLotesDeProductos(List<String> idsProducto) {
        if (idsProducto == null || idsProducto.isEmpty()) {
            return List.of();
        }
        return repositorioLote.findByProducto_IdProductoIn(idsProducto);
    }

}