package cl.ufro.dci.pds.inventario.dominio.catalogos.fabricantes;

import org.springframework.stereotype.Service;

@Service
public class ServicioFabricante {

    private final RepositorioFabricante repositorioFabricante;

    public ServicioFabricante(RepositorioFabricante repositorioFabricante) {
        this.repositorioFabricante = repositorioFabricante;
    }

    public Fabricante obtenerPorId(String idFabricante) {
        return repositorioFabricante.findById(idFabricante)
                .orElseThrow(() -> new FabricanteNoEncontradoException(idFabricante));
    }
}