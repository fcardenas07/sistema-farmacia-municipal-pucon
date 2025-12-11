package cl.ufro.dci.pds.fixtures.builders;

import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import cl.ufro.dci.pds.fixtures.mothers.ProductoMother;
import org.springframework.test.util.ReflectionTestUtils;

public class ProductoBuilder {
    private final Producto base = ProductoMother.baseDefault();

    public ProductoBuilder conNombreComercial(String nombre) {
        ReflectionTestUtils.setField(base, "nombreComercial", nombre);
        return this;
    }

    public ProductoBuilder sinFabricante() {
        base.setFabricante(null);
        return this;
    }

    public ProductoBuilder stock(int min, int max) {
        ReflectionTestUtils.setField(base, "stockMinimo", min);
        ReflectionTestUtils.setField(base, "stockMaximo", max);
        return this;
    }

    public ProductoBuilder inactivo() {
        ReflectionTestUtils.setField(base, "activo", false);
        return this;
    }

    public ProductoBuilder conCategoria(CategoriaProducto categoria) {
        ReflectionTestUtils.setField(base, "categoriaProducto", categoria);
        return this;
    }

    public Producto build() {
        return base;
    }
}

