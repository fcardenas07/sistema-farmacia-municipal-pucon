package cl.ufro.dci.pds.inventario.dominio.catalogos.productos;

public enum CategoriaProducto {
    ANALGESICOS_ANTIINFLAMATORIOS("Analgésicos y Antiinflamatorios"),
    ANTIBIOTICOS("Antibióticos"),
    ANTIVIRALES("Antivirales"),
    ANTIFUNGICOS("Antifúngicos"),
    ANTIHISTAMINICOS("Antihistamínicos"),
    GASTROINTESTINALES("Gastrointestinales"),
    RESPIRATORIOS("Respiratorios"),
    CARDIOVASCULARES("Cardiovasculares"),
    ENDOCRINOLOGIA("Endocrinología"),
    SALUD_MENTAL_NEUROLOGICOS("Neurológicos"),
    DERMATOLOGICOS("Dermatológicos"),
    OFTALMOLOGICOS("Oftalmológicos"),
    OTORRINOLARINGOLOGICOS("Otorrinolaringológicos"),
    VITAMINAS_SUPLEMENTOS("Vitaminas y suplementos"),
    PRODUCTOS_OTC("Venta libre"),
    OTROS("Otros");

    private final String nombreLegible;

    CategoriaProducto(String nombreLegible) {
        this.nombreLegible = nombreLegible;
    }

    public String getNombreLegible() {
        return nombreLegible;
    }
}
