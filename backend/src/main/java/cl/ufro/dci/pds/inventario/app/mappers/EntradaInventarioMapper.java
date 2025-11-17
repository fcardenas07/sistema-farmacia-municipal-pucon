package cl.ufro.dci.pds.inventario.app.mappers;

import cl.ufro.dci.pds.inventario.app.dtos.EntradaInventario;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import org.springframework.stereotype.Component;

@Component
public class EntradaInventarioMapper {

    public Lote toLote(EntradaInventario dto, Producto producto) {
        Lote lote = new Lote();
        lote.setIdLote(dto.idLote());
        lote.setNumeroLote(dto.numeroLote());
        lote.setFechaElaboracion(dto.fechaElaboracion());
        lote.setFechaVencimiento(dto.fechaVencimiento());
        lote.setEstado("ACTIVO");
        lote.setProducto(producto);
        return lote;
    }
}
