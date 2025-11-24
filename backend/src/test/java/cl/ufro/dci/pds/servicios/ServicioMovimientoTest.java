package cl.ufro.dci.pds.servicios;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import cl.ufro.dci.pds.inventario.dominio.control_stock.movimientos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioMovimientoTest {

    private RepositorioMovimiento repositorio;
    private ServicioMovimiento servicio;

    private Producto producto;
    private Lote lote;

    @BeforeEach
    void setUp() {
        repositorio = mock(RepositorioMovimiento.class);
        servicio = new ServicioMovimiento(repositorio);

        producto = new Producto();
        producto.setNombreComercial("Paracetamol");

        lote = new Lote();
    }

    @Test
    @DisplayName("registrarMovimientoPorBajaProducto crea movimiento con campos correctos y lo guarda")
    void registrarMovimientoPorBajaProducto() {
        int cantidad = 10;

        when(repositorio.save(any(Movimiento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var movimiento = servicio.registrarMovimientoPorBajaProducto(producto, lote, cantidad);

        assertNotNull(movimiento.getFechaMovimiento(), "La fecha de movimiento no debe ser nula");
        assertEquals(cantidad, movimiento.getCantidad(), "La cantidad debe ser la indicada");
        assertEquals(TipoMovimiento.BAJA, movimiento.getTipoMovimiento(), "El tipo de movimiento debe ser BAJA");
        assertEquals("Baja de 10 unidades de Paracetamol", movimiento.getDetalle(), "El detalle debe describir la baja correctamente");
        assertEquals(lote, movimiento.getLote(), "El lote debe ser el proporcionado");

        verify(repositorio).save(movimiento);
    }
}
