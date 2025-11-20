package cl.ufro.dci.pds.inventario.dominio.control_stock.stocks;

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
}
