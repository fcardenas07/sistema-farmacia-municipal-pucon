package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import cl.ufro.dci.pds.compartido.eventos.ItemVenta;
import cl.ufro.dci.pds.inventario.app.dtos.EntradaInventario;
import cl.ufro.dci.pds.inventario.app.mappers.EntradaInventarioMapper;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public List<Lote> obtenerLotesPorIds(List<String> ids){
        var lotes = repositorioLote.findAllById(ids);
        if(ids.size() != lotes.size()){
            throw new LotesIncompletosException("No todos los lotes fueron encontrados");
        }
        return lotes;
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

    @Transactional
    public List<Lote> reservarLotes(List<ItemVenta> solicitudes) {

        var ids = solicitudes.stream()
                .map(ItemVenta::idLote)
                .toList();

        var lotes = obtenerLotesPorIds(ids);

        var lotesPorId = lotes.stream()
                .collect(Collectors.toMap(Lote::getIdLote, Function.identity()));

        for (var sol : solicitudes) {
            var lote = lotesPorId.get(sol.idLote());
            if (lote == null) {
                throw new LoteInexistenteException(sol.idLote());
            }
            reservar(lote, sol.cantidad());
        }

        return lotes;
    }

    public void reservar(Lote lote, int cantidadSolicitada) {
        var stockActual = lote.getStockActual();
        var stockReservado = lote.getStockReservado();

        var stockDisponible = stockActual - stockReservado;

        if (stockDisponible < cantidadSolicitada) {
            throw new SinStockDisponibleException(lote.getNumeroLote());
        }

        lote.setStockReservado(stockReservado + cantidadSolicitada);
    }


    public void guardarTodos(List<Lote> lotes) {
        repositorioLote.saveAll(lotes);
    }
}