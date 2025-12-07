package cl.ufro.dci.pds.inventario.dominio.control_stock.lotes;

import cl.ufro.dci.pds.compartido.eventos.ItemVenta;
import cl.ufro.dci.pds.inventario.app.dtos.EntradaInventario;
import cl.ufro.dci.pds.inventario.app.mappers.EntradaInventarioMapper;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.ventas_facturacion_boletas.app.dtos.ItemLoteCantidad;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<Lote> obtenerPorVencerEntre(LocalDateTime hoy, LocalDateTime limite) {
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

    public void reservarLotes(List<ItemLoteCantidad> reservas) {
        for (var reserva: reservas) {
            var lote = reserva.lote();
            var cantidadSolicitada = reserva.cantidad();
            var stockReservado = lote.getStockReservado();

            if (lote.getStockDisponible() < cantidadSolicitada) {
                throw new SinStockDisponibleException(lote.getNumeroLote());
            }
            lote.setStockReservado(stockReservado + cantidadSolicitada);
        }

        var lotes = reservas.stream().map(ItemLoteCantidad::lote).toList();

        guardarTodos(lotes);
    }

    public void guardarTodos(List<Lote> lotes) {
        repositorioLote.saveAll(lotes);
    }

    public void consumirReserva(List<ItemLoteCantidad> items) {
        for (var item : items) {
            var lote = item.lote();
            var cantidad = item.cantidad();

            var descontado = descontar(lote, cantidad);

            var reservadoActual = lote.getStockReservado();
            lote.setStockReservado(Math.max(0, reservadoActual - descontado));

            repositorioLote.save(lote);
        }
    }

    public void liberarReserva(List<ItemLoteCantidad> items) {
        for (var item : items) {
            var lote = item.lote();
            var cantidad = item.cantidad();
            lote.setStockReservado(Math.max(0, lote.getStockReservado() - cantidad));

            repositorioLote.save(lote);
        }
    }
}