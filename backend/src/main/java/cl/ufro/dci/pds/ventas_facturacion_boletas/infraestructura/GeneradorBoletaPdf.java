package cl.ufro.dci.pds.ventas_facturacion_boletas.infraestructura;

import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.facturacion.boletas.Boleta;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.DetalleVenta;
import cl.ufro.dci.pds.ventas_facturacion_boletas.dominio.ventas.Venta;
import org.openpdf.text.*;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
public class GeneradorBoletaPdf {

    private static final String NOMBRE_FARMACIA = "Farmacia Municipal Pucón";
    private static final Locale LOCALE_CL = Locale.of("es", "CL");

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generarPdf(Boleta boleta) {
        try (var baos = new ByteArrayOutputStream()) {

            var documento = new Document(new Rectangle(600, 700)); // ajustable
            documento.setMargins(40, 40, 15, 15);

            PdfWriter.getInstance(documento, baos);
            documento.open();

            agregarEncabezado(documento, boleta);
            agregarDatosClienteYVendedor(documento, boleta);
            agregarSeparador(documento, 10f, 16f);
            agregarTablaDetalles(documento, boleta);
            agregarSeparador(documento, 20f, 16f);
            agregarTotal(documento, boleta);
            agregarMensajeDespedida(documento);

            documento.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de boleta", e);
        }
    }

    private void agregarEncabezado(Document doc, Boleta boleta) throws DocumentException {
        try {
            Image logo = Image.getInstance(
                    Objects.requireNonNull(getClass().getClassLoader().getResource("assets/logo/logo_farmacia.png"))
            );

            logo.scaleToFit(160, 160);
            logo.setAlignment(Image.ALIGN_CENTER);
            doc.add(logo);

        } catch (Exception e) {
            System.out.println("⚠ No se pudo cargar el logo: " + e.getMessage());
        }

        var titulo = new Paragraph(
                NOMBRE_FARMACIA,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 17)
        );
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingBefore(2f);
        titulo.setSpacingAfter(4f);
        doc.add(titulo);

        var info = new Paragraph(
                "Boleta Nº: " + boleta.getNumeroBoleta() + "\n" +
                        "Fecha: " + boleta.getFechaEmision().format(FORMATO_FECHA),
                FontFactory.getFont(FontFactory.HELVETICA, 10.5f)
        );
        info.setAlignment(Element.ALIGN_CENTER);
        info.setSpacingAfter(8f);
        doc.add(info);
    }

    private void agregarDatosClienteYVendedor(Document doc, Boleta boleta) throws DocumentException {
        var venta = boleta.getVenta();
        var cliente = venta.getCliente();
        var vendedor = venta.getUsuario();

        // cabecera compacta tipo boleta
        var texto = new Paragraph(
                "Cliente: " + cliente.getNombre() + "    " +
                        "RUT: " + cliente.getRutCliente() + "\n" +
                        "Vendedor: " + vendedor.getNombreCompleto(),
                FontFactory.getFont(FontFactory.HELVETICA, 10)
        );
        doc.add(texto);
    }

    private void agregarSeparador(Document doc,
                                  float espacioAntes,
                                  float espacioDespues) throws DocumentException {

        // Tabla de 1 columna que actúa como “regla” horizontal
        PdfPTable lineaTabla = new PdfPTable(1);
        lineaTabla.setWidthPercentage(100);
        lineaTabla.setSpacingBefore(espacioAntes);
        lineaTabla.setSpacingAfter(espacioDespues);

        PdfPCell cell = new PdfPCell(new Phrase("")); // sin texto
        cell.setBorderWidthTop(0.8f);      // grosor de la línea
        cell.setBorderWidthBottom(0f);
        cell.setBorderWidthLeft(0f);
        cell.setBorderWidthRight(0f);
        cell.setFixedHeight(2f);           // alto mínimo para que se dibuje bien
        cell.setPadding(0f);

        lineaTabla.addCell(cell);
        doc.add(lineaTabla);
    }

    private void agregarSeparador(Document doc) throws DocumentException {
        agregarSeparador(doc, 6f, 6f);
    }

    private void agregarTablaDetalles(Document doc, Boleta boleta) throws DocumentException {
        var venta = boleta.getVenta();
        var tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{1.2f, 6.5f, 2.2f, 2.2f});// Cant | Producto | Unit | Subtotal

        // Header
        agregarCeldaNumero(tabla, "Cant", true, Color.LIGHT_GRAY);
        agregarCeldaTexto(tabla, "Producto", true, Color.LIGHT_GRAY);
        agregarCeldaNumero(tabla, "P.Unit", true, Color.LIGHT_GRAY);
        agregarCeldaNumero(tabla, "Subtotal", true, Color.LIGHT_GRAY);

        var lineas = agruparDetallesPorProducto(venta);

        boolean alternador = false;
        for (LineaBoleta linea : lineas) {
            Color fondo = alternador ? new Color(240, 240, 240) : Color.WHITE;
            alternador = !alternador;

            // Cant y montos: alineados a la derecha
            agregarCeldaNumero(tabla, String.valueOf(linea.cantidadTotal()), false, fondo);
            agregarCeldaTexto(tabla, linea.nombreProducto(), false, fondo);
            agregarCeldaNumero(tabla, formatearDinero(linea.precioUnitario()), false, fondo);
            agregarCeldaNumero(tabla, formatearDinero(linea.subtotalTotal()), false, fondo);
        }

        doc.add(tabla);
    }

    private void agregarCeldaTexto(PdfPTable tabla, String texto, boolean esHeader, Color fondo) {
        var font = esHeader
                ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)
                : FontFactory.getFont(FontFactory.HELVETICA, 10);

        var cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(6);
        cell.setHorizontalAlignment(esHeader ? Element.ALIGN_CENTER : Element.ALIGN_LEFT);
        if (fondo != null) {
            cell.setBackgroundColor(fondo);
        }
        tabla.addCell(cell);
    }

    private void agregarCeldaNumero(PdfPTable tabla, String texto, boolean header, Color fondo) {
        var font = header
                ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)
                : FontFactory.getFont(FontFactory.HELVETICA, 10);

        var cell = new PdfPCell(new Phrase(texto, font));
        cell.setPadding(6);
        cell.setHorizontalAlignment(header ? Element.ALIGN_CENTER : Element.ALIGN_RIGHT);
        if (fondo != null) {
            cell.setBackgroundColor(fondo);
        }
        tabla.addCell(cell);
    }

    private List<LineaBoleta> agruparDetallesPorProducto(Venta venta) {
        record ClaveProducto(String nombre, int precioUnitario) {
        }

        var agrupados = new LinkedHashMap<ClaveProducto, LineaBoleta>();

        for (DetalleVenta detalle : venta.getDetalles()) {
            var nombreProducto = detalle.getLote()
                    .getCodigo()
                    .getProducto()
                    .getNombreComercial();

            int precioUnitario = detalle.getPrecioUnitario();
            int cantidad = detalle.getCantidad();
            int subtotal = detalle.getSubtotal();

            var clave = new ClaveProducto(nombreProducto, precioUnitario);

            agrupados.merge(
                    clave,
                    new LineaBoleta(nombreProducto, precioUnitario, cantidad, subtotal),
                    (existente, nueva) -> new LineaBoleta(
                            existente.nombreProducto(),
                            existente.precioUnitario(),
                            existente.cantidadTotal() + nueva.cantidadTotal(),
                            existente.subtotalTotal() + nueva.subtotalTotal()
                    )
            );
        }

        return List.copyOf(agrupados.values());
    }

    private void agregarTotal(Document doc, Boleta boleta) throws DocumentException {
        var tablaTotal = new PdfPTable(2);
        tablaTotal.setWidthPercentage(40);
        tablaTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaTotal.setWidths(new float[]{2.5f, 2f});

        var label = new PdfPCell(new Phrase("Total a pagar:",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        label.setHorizontalAlignment(Element.ALIGN_LEFT);
        label.setPadding(8);
        tablaTotal.addCell(label);

        var valor = new PdfPCell(new Phrase(formatearDinero(boleta.getMontoTotal()),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        valor.setHorizontalAlignment(Element.ALIGN_RIGHT);
        valor.setPadding(8);
        tablaTotal.addCell(valor);
        doc.add(tablaTotal);
    }

    private void agregarMensajeDespedida(Document doc) throws DocumentException {
        var p = new Paragraph(
                "\nGracias por su compra!",
                FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 11)
        );
        doc.add(p);
    }

    private String formatearDinero(long monto) {
        var formato = NumberFormat.getCurrencyInstance(LOCALE_CL);
        formato.setMaximumFractionDigits(0);
        formato.setMinimumFractionDigits(0);
        return formato.format(monto);
    }

    private record LineaBoleta(
            String nombreProducto,
            int precioUnitario,
            int cantidadTotal,
            int subtotalTotal
    ) {
    }
}
