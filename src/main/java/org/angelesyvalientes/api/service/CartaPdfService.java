package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.dto.CartaAgradecimientoDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CartaPdfService {

    // Puedes inyectar DonacionService si este servicio es independiente
    private final DonacionService donacionService;

    public CartaPdfService(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    public byte[] generarCartaAgradecimientoPdf(int donacionId) throws IOException {
        CartaAgradecimientoDTO datosCarta = donacionService.prepararDatosCartaAgradecimiento(donacionId);

        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.setLeading(14.5f); // Espacio entre líneas

                // Posición inicial
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float xStart = margin;
                contentStream.newLineAtOffset(xStart, yStart);

                // --- Encabezado ---
                contentStream.showText("Ángeles y Valientes");
                contentStream.newLine();
                contentStream.showText("Calle 50 #49 -09");
                contentStream.newLine();
                contentStream.showText("Fecha: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                contentStream.newLine();
                contentStream.newLine();

                // --- Saludo ---
                contentStream.showText("Estimado/a " + datosCarta.getNombreDonante() + " " + datosCarta.getApellidoDonante() + ",");
                contentStream.newLine();
                contentStream.newLine();

                // --- Cuerpo de la carta ---
                contentStream.showText("A nombre de todo el equipo de Ángeles y Valientes, queremos expresarle nuestro más sincero ");
                contentStream.newLine();
                contentStream.showText("agradecimiento por su reciente y generosa donación.");
                contentStream.newLine();
                contentStream.showText("Su apoyo es fundamental para continuar nuestra labor y hacer una diferencia real en la vida");
                contentStream.newLine();
                contentStream.showText(" de nuestros valientes. ");
                contentStream.newLine();
                contentStream.showText("Detalles de su donación:");
                contentStream.newLine();
                contentStream.newLine();

                contentStream.showText("  - Fecha de la Donación: " + datosCarta.getFechaDonacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                contentStream.newLine();
                contentStream.showText("  - Tipo de Donación: " + datosCarta.getTipoDonacion());
                contentStream.newLine();
                if (datosCarta.getObservacionDonacion() != null && !datosCarta.getObservacionDonacion().isEmpty()) {
                    contentStream.showText("  - Observaciones: " + datosCarta.getObservacionDonacion());
                    contentStream.newLine();
                }
                contentStream.newLine();
                contentStream.showText("Su contribución nos permite brindar apoyo vital a quienes más lo necesitan.");
                contentStream.newLine();
                contentStream.newLine();

                // --- Cierre ---
                contentStream.showText("Gracias por ser un Ángel para nuestros Valientes.");
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Atentamente,");
                contentStream.newLine();
                contentStream.showText("El Equipo de Ángeles y Valientes");

                contentStream.endText();
            }

            document.save(out);
            return out.toByteArray();
        }
    }
}