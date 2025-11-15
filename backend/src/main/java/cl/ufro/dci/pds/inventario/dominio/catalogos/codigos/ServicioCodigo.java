package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

import cl.ufro.dci.pds.inventario.app.dtos.CodigoModificado;
import org.springframework.stereotype.Service;

@Service
public class ServicioCodigo {

    private final RepositorioCodigo repositorioCodigo;

    public ServicioCodigo(RepositorioCodigo repositorioCodigo) {
        this.repositorioCodigo = repositorioCodigo;
    }

    public Codigo crear(Codigo codigo) {
        if (repositorioCodigo.existsById(codigo.getIdCodigo())) {
            throw new CodigoDuplicadoException(codigo.getIdCodigo());
        }
        return repositorioCodigo.save(codigo);
    }

    public Codigo actualizarParaProducto(String idProducto, CodigoModificado dto) {
        var codigo = repositorioCodigo.findByIdAndProductoId(dto.idCodigo(), idProducto)
                .orElseThrow(() -> new CodigoNoEncontradoException(dto.idCodigo()));

        dto.aplicarCambios(codigo);
        return repositorioCodigo.save(codigo);
    }
}
