package cl.ufro.dci.pds.infraestructura.email;

import cl.ufro.dci.pds.inventario.dominio.control_stock.lotes.Lote;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailContentBuilder {

    public String construirTablaLotesPorVencer(List<Lote> lotes) {

        StringBuilder sb = new StringBuilder();

        sb.append("""
        <html><body>
        <h2>Lotes próximos a vencer</h2>
        <p>Estos lotes vencen en los próximos 30 días:</p>

        <table border='1' cellpadding='6' cellspacing='0' style='border-collapse: collapse;'>
            <tr>
                <th>Código</th>
                <th>Producto</th>
                <th>Número Lote</th>
                <th>F. Elaboración</th>
                <th>F. Vencimiento</th>
                <th>Stock</th>
                <th>Estado</th>
            </tr>
        """);

        for (var l : lotes) {
            sb.append("<tr>")
                    .append("<td>").append(l.getCodigo().getIdCodigo()).append("</td>")
                    .append("<td>").append(l.getCodigo().getProducto().getNombreComercial()).append("</td>")
                    .append("<td>").append(l.getNumeroLote()).append("</td>")
                    .append("<td>").append(l.getFechaElaboracion()).append("</td>")
                    .append("<td>").append(l.getFechaVencimiento()).append("</td>")
                    .append("<td>").append(l.getStockActual()).append("</td>")
                    .append("<td>").append(l.getEstado()).append("</td>")
                    .append("</tr>");
        }

        sb.append("</table></body></html>");

        return sb.toString();
    }
}
