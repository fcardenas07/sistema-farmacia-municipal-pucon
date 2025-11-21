package cl.ufro.dci.pds.inventario.dominio.control_stock.stocks;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;

@Service
public class ServicioStock {
    private final RepositorioStock repositorioStock;

    public ServicioStock(RepositorioStock repositorioStock) {
        this.repositorioStock = repositorioStock;
    }

    public int darBaja(Stock stock) {
        var cantidadActual = stock.getCantidadActual();
        stock.setCantidadActual(0);
        repositorioStock.save(stock);
        return cantidadActual;
    }

    public Stock crear(Lote lote, int cantidad) {
        var stock = new Stock();
        stock.setCantidadActual(cantidad);
        stock.setCantidadInicial(cantidad);
        stock.setLote(lote);
        return repositorioStock.save(stock);
    }
}
