package cl.ufro.dci.pds.inventario.infraestructura;

import cl.ufro.dci.pds.inventario.app.dtos.MovimientoBuscado;
import cl.ufro.dci.pds.inventario.app.dtos.TrazabilidadIngreso;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.Movimiento;
import org.springframework.stereotype.Component;


@Component
public class TrazabilidadLoteMapper {

    public TrazabilidadIngreso toDto(ProyeccionTrazabilidadIngreso p) {
        return new TrazabilidadIngreso(
                p.getIdCodigo(),
                p.getNombreComercial(),
                p.getNumeroLote(),
                p.getFechaElaboracion(),
                p.getFechaVencimiento(),
                p.getCantidad(),
                p.getEstado(),
                p.getIdMovimiento(),
                p.getFechaMovimiento(),
                p.getTipoMovimiento()
        );
    }

    public MovimientoBuscado toDto(Movimiento m){
        return new MovimientoBuscado(
                m.getIdMovimiento(),
                m.getTipoMovimiento().getNombreLegible(),
                m.getCantidad(),
                m.getFechaMovimiento().toString(),
                m.getDetalle()
        );
    }
}

