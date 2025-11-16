package cl.ufro.dci.pds.inventario.dominio.catalogos.codigos;

import cl.ufro.dci.pds.inventario.app.dtos.CodigoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.CodigoAModificar;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoAModificar;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ServicioCodigo {

    private final RepositorioCodigo repositorioCodigo;

    public ServicioCodigo(RepositorioCodigo repositorioCodigo) {
        this.repositorioCodigo = repositorioCodigo;
    }

    public Codigo crear(Producto producto, CodigoACrear nuevoCodigo) {
        if (repositorioCodigo.existsById(nuevoCodigo.idCodigo())) {
            throw new CodigoDuplicadoException(nuevoCodigo.idCodigo());
        }

        var codigo = nuevoCodigo.aEntidad(producto);
        return repositorioCodigo.save(codigo);
    }

    public Codigo actualizarParaProducto(String idProducto, CodigoAModificar dto) {
        if (!repositorioCodigo.existsById(dto.idCodigo())) {
            throw new CodigoNoEncontradoException(dto.idCodigo());
        }

        var codigo = repositorioCodigo.findByIdCodigoAndProducto_IdProducto(dto.idCodigo(), idProducto)
                .orElseThrow(() -> new CodigoNoPerteneceProductoException(dto.idCodigo(), idProducto));

        dto.aplicarCambios(codigo);
        return repositorioCodigo.save(codigo);
    }

    public List<Codigo> obtenerCodigosConIdProducto(String idProducto) {
        return repositorioCodigo.findAllByProducto_IdProducto(idProducto);
    }
}
