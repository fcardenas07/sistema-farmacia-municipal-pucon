package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

import cl.ufro.dci.pds.inventario.app.dtos.NuevoProducto;
import cl.ufro.dci.pds.inventario.app.dtos.ProductoModificado;
import org.springframework.stereotype.Service;

@Service
public class ServicioProducto {

    private final RepositorioProducto repositorioProducto;

    public ServicioProducto(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    public Producto crear(NuevoProducto dto) {

        var idProducto = dto.idProducto();

        if (repositorioProducto.findById(idProducto).isPresent()) {
            throw new ProductoDuplicadoException(idProducto);
        }
        Producto p = new Producto();

        p.setIdProducto(idProducto);
        p.setNombreComercial(dto.nombreComercial());
        p.setNombreGenerico(dto.nombreGenerico());
        p.setPresentacion(dto.presentacion());
        p.setDosificacion(dto.dosificacion());
        p.setUnidadMedida(dto.unidadMedida());
        p.setStockMinimo(dto.stockMinimo());
        p.setStockMaximo(dto.stockMaximo());
        p.setEstado(dto.estado());

        return repositorioProducto.save(p);
    }

    public Producto actualizar(String id, ProductoModificado dto) {
        Producto p = repositorioProducto.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        if(dto.nombreComercial() != null) p.setNombreComercial(dto.nombreComercial());
        if(dto.nombreGenerico() != null) p.setNombreGenerico(dto.nombreGenerico());
        if(dto.presentacion() != null) p.setPresentacion(dto.presentacion());
        if(dto.dosificacion() != null) p.setDosificacion(dto.dosificacion());
        if(dto.unidadMedida() != null) p.setUnidadMedida(dto.unidadMedida());
        if(dto.stockMinimo() != null) p.setStockMinimo(dto.stockMinimo());
        if(dto.stockMaximo() != null) p.setStockMaximo(dto.stockMaximo());
        if(dto.estado() != null) p.setEstado(dto.estado());

        return repositorioProducto.save(p);
    }
}
