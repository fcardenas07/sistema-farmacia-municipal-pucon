package cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public Movimiento registrarMovimientoPorMerma(Lote lote, int cantidad, String motivo) {
        var movimiento = new Movimiento();
        movimiento.setLote(lote);
        movimiento.setFechaMovimiento(LocalDate.now());
        movimiento.setCantidad(cantidad);
        movimiento.setTipoMovimiento(TipoMovimiento.MERMA);
        movimiento.setDetalle("Merma de " + cantidad + " unidades en lote " + lote.getNumeroLote() +
                (motivo != null && !motivo.isBlank() ? " - Motivo: " + motivo : ""));

        return repositorioMovimiento.save(movimiento);
    }

    public Movimiento obtenerPorId(String idMovimiento) {
        return repositorioMovimiento.findById(idMovimiento)
                .orElseThrow(() -> new MovimientoNoEncontradoException(idMovimiento));
    }

    public Page<Movimiento> obtenerPorTipoMovimiento(TipoMovimiento tipoMovimiento, Pageable pageable) {
        if (tipoMovimiento == null) {
            return repositorioMovimiento.findAll(pageable);
        }

        return repositorioMovimiento.findByTipoMovimiento(tipoMovimiento, pageable);
    }
}
