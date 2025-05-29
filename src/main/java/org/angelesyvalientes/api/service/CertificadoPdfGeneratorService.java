package org.angelesyvalientes.api.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.angelesyvalientes.api.dto.CertificadoDonacionDTO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CertificadoPdfGeneratorService {

    private final DonacionService donacionService;

    public CertificadoPdfGeneratorService(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    public byte[] generarCertificadoDonacionPdf(int donacionId) throws JRException, IOException {
        CertificadoDonacionDTO datosCertificado = donacionService.prepararDatosCertificadoDonacion(donacionId);

        // Cargar la plantilla JRXML (debe estar en src/main/resources/reports/)
        InputStream jasperStream = new ClassPathResource("reports/certificado_donacion.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);

        // Crear una fuente de datos con un solo objeto DTO
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(datosCertificado));

        // Parámetros (si tu plantilla usa parámetros adicionales)
        Map<String, Object> parameters = new HashMap<>();
        // parameters.put("logoPath", new ClassPathResource("static/images/logo.png").getFile().getAbsolutePath()); // Ejemplo para pasar la ruta del logo

        // Llenar el informe
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Exportar a PDF
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));

        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        return byteArrayOutputStream.toByteArray();
    }
}