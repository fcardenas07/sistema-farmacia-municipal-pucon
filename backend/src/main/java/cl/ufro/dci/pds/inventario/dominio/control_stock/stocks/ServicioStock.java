package cl.ufro.dci.pds.inventario.dominio.control_stock.stocks;

import org.springframework.stereotype.Service;

@Service
public class ServicioStock {

    private final RepositorioStock repositorioStock;
    public ServicioStock(RepositorioStock repositorioStock) {
        this.repositorioStock = repositorioStock;
    }
}
