package cl.ufro.dci.pds.inventario.app.mappers;

import cl.ufro.dci.pds.inventario.app.dtos.EntradaIngresada;
import cl.ufro.dci.pds.inventario.app.dtos.EntradaInventario;
import cl.ufro.dci.pds.inventario.dominio.abastecimiento.guiasingreso.GuiaIngreso;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.Codigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.Stock;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

@Component
public class EntradaInventarioMapper {

    public Lote toLote(EntradaInventario dto, Codigo codigo, @Nullable GuiaIngreso guiaIngreso) {
        Lote lote = new Lote();
        lote.setFechaElaboracion(dto.fechaElaboracion());
        lote.setFechaVencimiento(dto.fechaVencimiento());
        lote.setNumeroLote(dto.numeroLote());
        lote.setEstado(dto.estado());
        lote.setPrecioUnitario(dto.precioUnitario());
        lote.setLimiteMerma(dto.limiteMerma());
        lote.setPorcentajeOferta(dto.porcentajeOferta());
        lote.setCodigo(codigo);
        lote.setGuiaIngreso(guiaIngreso);

        return lote;
    }

    public EntradaIngresada toEntradaIngresada(Lote lote, Producto producto, Codigo codigo, Stock stock, @Nullable GuiaIngreso guiaIngreso) {

        return new EntradaIngresada(
                lote.getIdLote(),
                lote.getNumeroLote(),
                stock.getCantidadActual(),

                // DatosProducto
                new EntradaIngresada.DatosProducto(
                        producto.getIdProducto(),
                        producto.getNombreComercial(),
                        producto.getCategoria() != null ? producto.getCategoria() : null
                ),

                // DatosCodigo
                new EntradaIngresada.DatosCodigo(
                        codigo.getIdCodigo(),
                        codigo.getCodigoBarra(),
                        codigo.getTipoCodigo()
                ),

                guiaIngreso != null ? guiaIngreso.getIdGuiaIngreso() : null,

                lote.getFechaElaboracion().toString(),
                lote.getFechaVencimiento().toString(),
                lote.getEstado()
        );
    }}
