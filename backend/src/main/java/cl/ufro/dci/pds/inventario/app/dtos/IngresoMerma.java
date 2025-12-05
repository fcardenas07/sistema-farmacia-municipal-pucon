package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.mermas.Merma;
import cl.ufro.dci.pds.inventario.dominio.control_stock.mermas.TipoMerma;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record IngresoMerma(
        @NotBlank String idLote,
        @NotNull TipoMerma tipoMerma,
        @NotBlank @Size(min = 10, max = 500) String detalle,
        @NotNull @Positive Integer cantidad
) {
    public Merma aEntidad(Lote lote) {
        var merma = new Merma();
        merma.setFechaMerma(LocalDate.now());
        merma.setDetalle(detalle);
        merma.setTipoMerma(tipoMerma);
        merma.setLote(lote);
        return merma;
    }
}
