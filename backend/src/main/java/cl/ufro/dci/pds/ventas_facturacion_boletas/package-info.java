@org.springframework.modulith.ApplicationModule(
        allowedDependencies = {
                "usuarios_permisos :: usuarios",
                "pacientes :: inscripcion",
                "inventario :: lotes"
        }
)
package cl.ufro.dci.pds.ventas_facturacion_boletas;