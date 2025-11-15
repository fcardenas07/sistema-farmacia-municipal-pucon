package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

import cl.ufro.dci.pds.inventario.app.dtos.CodigoModificado;
import cl.ufro.dci.pds.inventario.app.dtos.NuevoCodigo;
import org.springframework.stereotype.Service;

@Service
public class ServicioCodigo {

    private final RepositorioCodigo repositorioCodigo;

    public ServicioCodigo(RepositorioCodigo repositorioCodigo) {
        this.repositorioCodigo = repositorioCodigo;
    }

    public Codigo crear(NuevoCodigo nuevoCodigo) {
        if (repositorioCodigo.existsById(nuevoCodigo.idCodigo())) {
            throw new CodigoDuplicadoException(nuevoCodigo.idCodigo());
        }

        var codigo = nuevoCodigo.aEntidad();
        return repositorioCodigo.save(codigo);
    }

    public Codigo actualizarParaProducto(String idProducto, CodigoModificado dto) {
        var codigo = repositorioCodigo.findByIdAndProductoId(dto.idCodigo(), idProducto)
                .orElseThrow(() -> new CodigoNoEncontradoException(dto.idCodigo()));

        dto.aplicarCambios(codigo);
        return repositorioCodigo.save(codigo);
    }
}
