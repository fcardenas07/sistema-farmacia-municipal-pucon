package cl.ufro.dci.pds.inventario.infraestructura;

import cl.ufro.dci.pds.inventario.app.dtos.TrazabilidadLote;
import org.springframework.stereotype.Component;


@Component
public class TrazabilidadLoteMapper {

    public TrazabilidadLote toDto(TrazabilidadProjection p) {
        return new TrazabilidadLote(
                p.getIdCodigo(),
                p.getNombreComercial(),
                p.getCodigoLote(),
                p.getFechaElaboracion(),
                p.getFechaVencimiento(),
                p.getCantidad(),
                p.getEstado(),
                p.getUltimoMovimiento(),
                p.getTipoMovimiento()
        );
    }
}

