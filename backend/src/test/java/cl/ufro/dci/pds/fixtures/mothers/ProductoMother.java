package cl.ufro.dci.pds.fixtures.mothers;

import cl.ufro.dci.pds.inventario.dominio.catalogos.fabricantes.Fabricante;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.CategoriaProducto;
import cl.ufro.dci.pds.inventario.dominio.catalogos.productos.Producto;
import org.springframework.test.util.ReflectionTestUtils;

public class ProductoMother {
    private static Producto crearProducto(
            String idProducto,
            String idFabricante,
            String nombreComercial,
            String nombreGenerico,
            String presentacion,
            Integer dosificacion,
            String unidad,
            Integer stockMin,
            Integer stockMax,
            boolean activo,
            CategoriaProducto categoria,
            String urlFoto
    ) {

        var producto = new Producto(
                nombreComercial,
                nombreGenerico,
                presentacion,
                dosificacion,
                unidad,
                stockMin,
                stockMax,
                activo,
                categoria,
                urlFoto
        );

        if (idFabricante != null) {
            var fabricante = new Fabricante();
            ReflectionTestUtils.setField(fabricante, "idFabricante", idFabricante);
            producto.setFabricante(fabricante);
        }

        if (idProducto != null) {
            ReflectionTestUtils.setField(producto, "idProducto", idProducto);
        }

        return producto;
    }

    public static Producto paracetamol() {
        return crearProducto(
                "P001", "F001",
                "Paracetamol", "Paracetamol", "Tabletas",
                500, "mg", 10, 1000, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0001.jpg"
        );
    }

    public static Producto paracetamolSinId() {
        return crearProducto(
                null, null,
                "Paracetamol", "Paracetamol", "Tabletas",
                500, "mg", 10, 1000, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0001.jpg"
        );
    }

    public static Producto ibuprofeno() {
        return crearProducto(
                "P002", "F002",
                "Advil", "Ibuprofeno", "Tabletas",
                400, "mg", 5, 50, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0002.jpg"
        );
    }

    public static Producto ibuprofenoSinId() {
        return crearProducto(
                null, null,
                "Advil", "Ibuprofeno", "Tabletas",
                400, "mg", 5, 50, true,
                CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/P0002.jpg"
        );
    }

    public static Producto amoxicilina() {
        return crearProducto(
                "P003", "F003",
                "Amoxil", "Amoxicilina", "Caja 12 cápsulas",
                500, "mg", 20, 200, false,
                CategoriaProducto.ANTIBIOTICOS,
                "producto/P0003.jpg"
        );
    }

    public static Producto amoxicilinaSinId() {
        return crearProducto(
                null, null,
                "Amoxil", "Amoxicilina", "Caja 12 cápsulas",
                500, "mg", 20, 200, false,
                CategoriaProducto.ANTIBIOTICOS,
                "producto/P0003.jpg"
        );
    }

    public static Producto baseDefault() {
        return crearProducto(
                null, null,
                "Producto Default", "Generico", "Tabletas",
                500, "mg", 10, 100,
                true, CategoriaProducto.ANALGESICOS_ANTIINFLAMATORIOS,
                "producto/default.jpg"
        );
    }
}
