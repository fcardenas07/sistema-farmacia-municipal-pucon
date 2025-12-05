package cl.ufro.dci.pds.inventario.dominio.control_stock.mermas;

import org.springframework.stereotype.Service;

@Service
public class ServicioMerma {

    private final RepositorioMerma repositorioMerma;

    public ServicioMerma(RepositorioMerma repositorioMerma) {
        this.repositorioMerma = repositorioMerma;
    }

    public Merma guardar(Merma merma) {
        return repositorioMerma.save(merma);
    }
}
