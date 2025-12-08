package cl.ufro.dci.pds.testmother.inventario.objectmother;

import cl.ufro.dci.pds.inventario.app.dtos.CodigoACrear;
import cl.ufro.dci.pds.inventario.app.dtos.EntradaInventario;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

public final class EntradaInventarioMother {

    private static final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules();

    private EntradaInventarioMother() {
    }

    public static EntradaInventario crearValida() {
        return new EntradaInventario(
                LocalDateTime.now().minusDays(30),  // fechaElaboracion
                LocalDateTime.now().plusMonths(12), // fechaVencimiento
                "ACTIVO",                            // estado
                "L-12345",                          // numeroLote
                100,                                // cantidad
                5,                                  // limiteMerma
                0.0f,                               // porcentajeOferta
                500,                                // precioUnitario
                "G-2025-001",                       // idGuiaIngreso
                new CodigoACrear("COD123", "7891234567890", true, "P001")
        );
    }

    public static EntradaInventario crearConCamposVacios(){
        return new EntradaInventario(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public static EntradaInventario conCantidadCero() {
        var base = crearValida();
        return new EntradaInventario(
                base.fechaElaboracion(),
                base.fechaVencimiento(),
                base.estado(),
                base.numeroLote(),
                0,
                base.limiteMerma(),
                base.porcentajeOferta(),
                base.precioUnitario(),
                base.idGuiaIngreso(),
                base.codigo()
        );
    }

    public static EntradaInventario conFechasInvertidas() {
        return new EntradaInventario(
                LocalDateTime.now(),                // elaboración
                LocalDateTime.now().minusDays(1),   // vencimiento inválido
                "Bueno",
                "L-999",
                50,
                3,
                5f,
                800,
                "G-ERROR",
                new CodigoACrear("COD123", "7891234567890", true, "P001")
        );
    }

    public static EntradaInventario sinEstado() {
        var base = crearValida();
        return new EntradaInventario(
                base.fechaElaboracion(),
                base.fechaVencimiento(),
                null,                  // estado inválido (NotBlank)
                base.numeroLote(),
                base.cantidad(),
                base.limiteMerma(),
                base.porcentajeOferta(),
                base.precioUnitario(),
                base.idGuiaIngreso(),
                base.codigo()
        );
    }

    public static EntradaInventario conPrecioInvalido() {
        var base = crearValida();
        return new EntradaInventario(
                base.fechaElaboracion(),
                base.fechaVencimiento(),
                base.estado(),
                base.numeroLote(),
                base.cantidad(),
                base.limiteMerma(),
                base.porcentajeOferta(),
                -1,        // precio < 0 => inválido
                base.idGuiaIngreso(),
                base.codigo()
        );
    }

    public static String jsonValido() {
        try {
            return mapper.writeValueAsString(crearValida());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String jsonCamposVacios() {
        try {
            return mapper.writeValueAsString(crearConCamposVacios());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
