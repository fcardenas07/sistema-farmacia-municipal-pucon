package cl.ufro.dci.pds.inventario.infraestructura;

import cl.ufro.dci.pds.inventario.app.dtos.TrazabilidadIngreso;
import org.springframework.stereotype.Component;


@Component
public class TrazabilidadLoteMapper {

    public TrazabilidadIngreso toDto(TrazabilidadIngresoProjection p) {
        return new TrazabilidadIngreso(
                p.getIdCodigo(),
                p.getNombreComercial(),
                p.getNumeroLote(),
                p.getFechaElaboracion(),
                p.getFechaVencimiento(),
                p.getCantidad(),
                p.getEstado(),
                p.getFechaMovimiento(),
                p.getTipoMovimiento()
        );
    }
}

