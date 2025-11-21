package cl.ufro.dci.pds.inventario.app.dtos;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record EntradaInventario(

        @NotNull(message = "La fecha de elaboración es obligatoria")
        LocalDate fechaElaboracion,

        @NotNull(message = "La fecha de vencimiento es obligatoria")
        LocalDate fechaVencimiento,

        @NotBlank(message = "El estado es obligatorio")
        String estado,

        @NotBlank(message = "El número de lote es obligatorio")
        String numeroLote,

        @NotNull(message = "La cantidad es obligatoria")
        @Positive(message = "La cantidad debe ser mayor a 0")
        Integer cantidad,

        @PositiveOrZero(message = "El límite de merma debe ser >= 0")
        Integer limiteMerma,

        @PositiveOrZero(message = "El porcentaje de oferta debe ser >= 0")
        Float porcentajeOferta,

        @NotNull(message = "El precio unitario es obligatorio")
        @Positive(message = "El precio debe ser mayor a 0")
        Integer precioUnitario,

        String idGuiaIngreso,

        @Valid
        CodigoACrear codigo

) {}
