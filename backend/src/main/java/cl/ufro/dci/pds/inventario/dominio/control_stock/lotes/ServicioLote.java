package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import cl.ufro.dci.pds.inventario.app.dtos.EntradaInventario;
import cl.ufro.dci.pds.inventario.app.mappers.EntradaInventarioMapper;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.RepositorioProducto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioLote {

    private final RepositorioLote repositorioLote;
    private final RepositorioProducto repositorioProducto;
    private final EntradaInventarioMapper mapper;

    public ServicioLote(RepositorioLote repositorioLote,
                        RepositorioProducto repositorioProducto,
                        EntradaInventarioMapper  mapper) {
        this.repositorioLote = repositorioLote;
        this.repositorioProducto = repositorioProducto;
        this.mapper = mapper;
    }



    public Lote crear(EntradaInventario entrada) {

        var existente = repositorioLote.findById(entrada.idLote());
        if (existente.isPresent()) {
            return existente.get();
        }
        var producto = repositorioProducto.findById(entrada.idProducto())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Lote lote = mapper.toLote(entrada, producto);
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