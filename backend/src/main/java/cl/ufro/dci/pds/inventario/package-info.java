@org.springframework.modulith.ApplicationModule(
        allowedDependencies = {
                "usuarios_permisos :: usuarios",
                "pacientes :: inscripcion",
                "ventas_facturacion_boletas :: ventas",
                "ventas_facturacion_boletas :: devoluciones",


        }
)
package cl.ufro.dci.pds.inventario;