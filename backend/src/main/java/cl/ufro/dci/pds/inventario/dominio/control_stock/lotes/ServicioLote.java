package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import cl.ufro.dci.pds.inventario.app.dtos.EntradaInventario;
import cl.ufro.dci.pds.inventario.app.mappers.EntradaInventarioMapper;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        var lote = mapper.toLote(dto, codigo, null); //por ahora que guiaingreso no esta implementado
        return repositorioLote.save(lote);
    }

    public Lote obtenerPorId(String idLote) {
        return repositorioLote.findById(idLote)
                .orElseThrow(() -> new LoteNoEncontradoException(idLote));
    }

    public List<Lote> obtenerLotesDeCodigos(List<String> idsCodigo) {
        if (idsCodigo == null || idsCodigo.isEmpty()) {
            return List.of();
        }
        return repositorioLote.findByCodigo_IdCodigoIn(idsCodigo);
    }

    public List<Lote> obtener(){
        return repositorioLote.findAll();
    }

    public List<Lote> obtenerPorVencerEntre(LocalDate hoy, LocalDate limite) {
        return repositorioLote.findPorVencerEntre(hoy, limite);
    }

    public List<Lote> obtenerPorNumeroLote(String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return repositorioLote.findAll();
        }

        return repositorioLote.findByNumeroLoteStartingWithIgnoreCase(filtro);
    }

    public int darBaja(Lote lote) {
        var cantidadBajada = lote.getStockActual();
        lote.setStockActual(0);
        lote.setStockReservado(0);
        lote.setEstado("INACTIVO");
        repositorioLote.save(lote);
        return cantidadBajada;
    }

    public int descontar(Lote lote, int cantidadSolicitada) {
        var stockActual = lote.getStockActual();

        if (stockActual == 0) {
            throw new SinStockDisponibleException(lote.getNumeroLote());
        }

        var descontado = Math.min(cantidadSolicitada, stockActual);

        lote.setStockActual(stockActual - descontado);
        repositorioLote.save(lote);

        return descontado;
    }

    public void guardarTodos(List<Lote> lotes) {
        repositorioLote.saveAll(lotes);
    }
}