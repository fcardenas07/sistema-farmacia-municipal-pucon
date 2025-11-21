package cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ServicioMovimiento {

    private final RepositorioMovimiento repositorioMovimiento;

    public ServicioMovimiento(RepositorioMovimiento repositorioMovimiento) {
        this.repositorioMovimiento = repositorioMovimiento;
    }

    public Movimiento registarMovimientoPorEntradaInventario(Lote lote, int cantidad, String nombreComercial) {
        var movimiento = new Movimiento();
        movimiento.setLote(lote);
        movimiento.setFechaMovimiento(LocalDate.now());
        movimiento.setCantidad(cantidad);
        movimiento.setTipoMovimiento(TipoMovimiento.INGRESO);
        movimiento.setDetalle("Ingreso de " + cantidad + " unidades del lote: " + lote.getNumeroLote() + " del producto: " + nombreComercial);
        return repositorioMovimiento.save(movimiento);
    }

    public Movimiento registrarMovimientoPorBajaProducto(Producto producto, Lote lote, int cantidad) {
        var movimiento = new Movimiento();
        movimiento.setLote(lote);
        movimiento.setFechaMovimiento(LocalDate.now());
        movimiento.setCantidad(cantidad);
        movimiento.setTipoMovimiento(TipoMovimiento.BAJA);
        var nombreProducto = producto.getNombreComercial();
        movimiento.setDetalle("Baja de " + cantidad + " unidades de " + nombreProducto);
        System.out.println("Movimiento: " + movimiento.getTipoMovimiento().getNombreLegible() + ": " + nombreProducto);

        return repositorioMovimiento.save(movimiento);
    }
}
