package cl.ufro.dci.pds.inventario.app.servicios;

import cl.ufro.dci.pds.inventario.app.dtos.EntradaIngresada;
import cl.ufro.dci.pds.inventario.app.dtos.EntradaInventario;
import cl.ufro.dci.pds.inventario.app.mappers.EntradaInventarioMapper;
import cl.ufro.dci.pds.inventario.dominio.catalogos.codigos.ServicioCodigo;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.ServicioProducto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.ServicioLote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.ServicioMovimiento;
import cl.ufro.dci.pds.inventario.dominio.control_stock.stocks.ServicioStock;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ServicioAppInventario {

    private final ServicioLote servicioLote;
    private final ServicioStock servicioStock;
    private final ServicioCodigo  servicioCodigo;
    private final ServicioProducto  servicioProducto;
    private final ServicioMovimiento servicioMovimiento;
    private final EntradaInventarioMapper mapper;

    public ServicioAppInventario(ServicioLote servicioLote,
                                 ServicioStock servicioStock,
                                 ServicioCodigo servicioCodigo,
                                 ServicioProducto servicioProducto, ServicioMovimiento servicioMovimiento, EntradaInventarioMapper mapper) {
        this.servicioLote = servicioLote;
        this.servicioStock = servicioStock;
        this.servicioCodigo = servicioCodigo;
        this.servicioProducto = servicioProducto;
        this.servicioMovimiento = servicioMovimiento;
        this.mapper = mapper;
    }

    @Transactional
    public EntradaIngresada crearLote(EntradaInventario dto){
        var producto = servicioProducto.obtenerPorId(dto.codigo().idProducto());
        var codigo = servicioCodigo.obtenerOCrear(producto, dto.codigo());
        var lote = servicioLote.crear(dto, codigo);
        var stock = servicioStock.crear(lote, dto.cantidad());
        servicioMovimiento.registarMovimientoPorEntradaInventario(lote, dto.cantidad(), producto.getNombreComercial());
        return mapper.toEntradaIngresada(lote, producto, codigo, stock, null);
    }
}
