package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import cl.ufro.dci.pds.inventario.app.dtos.EntradaInventario;
import cl.ufro.dci.pds.inventario.app.mappers.EntradaInventarioMapper;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.RepositorioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.RepositorioProducto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioLote {

    private final RepositorioLote repositorioLote;
    private final EntradaInventarioMapper mapper;

    public ServicioLote(RepositorioLote repositorioLote,
                        EntradaInventarioMapper  mapper) {
        this.repositorioLote = repositorioLote;
        this.mapper = mapper;
    }

    public Lote crear(EntradaInventario dto, Codigo codigo) {
        Lote lote = mapper.toLote(dto, codigo, null); //por ahora que guiaingreso no esta implementado
        return repositorioLote.save(lote);
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