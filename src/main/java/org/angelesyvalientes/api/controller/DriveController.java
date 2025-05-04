package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.security.Res;
import org.angelesyvalientes.api.service.DocumentacionService;
import org.angelesyvalientes.api.service.GoogleDriveService;
import org.angelesyvalientes.api.service.InformeClinicoService;
import org.angelesyvalientes.api.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Controlador REST para la carga de archivos a Google Drive.
 */
@Tag(name = "Carga a Drive")
@RestController
public class DriveController {

    private static final Logger logger = LoggerFactory.getLogger(DriveController.class);

    private final GoogleDriveService googleDriveService;
    private final PersonaService personaService;
    private final DocumentacionService documentacionService;
    private final InformeClinicoService informeClinicoService;

    @Autowired
    public DriveController(GoogleDriveService googleDriveService, PersonaService personaService, DocumentacionService documentacionService, InformeClinicoService informeClinicoService) {
        this.googleDriveService = googleDriveService;
        this.personaService = personaService;
        this.documentacionService=documentacionService;
        this.informeClinicoService = informeClinicoService;

    }

    /**
     * Endpoint para la carga de un archivo de imagen a Google Drive.
     *
     * @param file      Archivo de imagen enviado como multipart/form-data.
     * @param idPersona ID de la persona para actualizar su foto de perfil.
     * @return Respuesta con la URL de la imagen subida o un mensaje de error.
     */
    @Operation(summary = "Cargar un archivo al Drive")
    @PostMapping("/uploadToGoogleDrive")
    public ResponseEntity<?> handleFileUpload(@RequestParam("image") MultipartFile file,
                                              @RequestParam(value = "idPersona", required = false) Long idPersona) throws IOException, GeneralSecurityException {

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo está vacío.");
        }

        // Validar que el archivo es una imagen
        if (!file.getContentType().startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo no es una imagen válida.");
        }

        File tempFile = File.createTempFile("temp", null);
        try {
            file.transferTo(tempFile);
            Res res = googleDriveService.uploadImageToDrive(tempFile, idPersona);
            logger.info("Imagen subida exitosamente: {}", res);

            // Actualizar la foto de perfil si se proporciona el ID de persona
            if (idPersona != null && res.getUrl() != null) {
                try {
                    personaService.actualizarUrlFoto(idPersona, res.getUrl());
                } catch (Exception e) {
                    logger.warn("Error al actualizar la foto de perfil de la persona con ID {}: {}", idPersona, e.getMessage());
                }
            }
            return ResponseEntity.ok(res);

        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
    @Operation(summary = "Subir un PDF y asociarlo a la persona y tipo de documento")
    @PostMapping("/uploadPdfToGoogleDrive")
    public ResponseEntity<?> handlePdfUpload(@RequestParam("pdf") MultipartFile file,
                                             @RequestParam("idPersona") Long idPersona,
                                             @RequestParam("tipoDocumentacion") String tipoDocumentacion) throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo está vacío.");
        }

        if (!file.getContentType().equals("application/pdf")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo debe ser un PDF.");
        }

        if (tipoDocumentacion == null || tipoDocumentacion.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tipo de documento es obligatorio.");
        }

        File tempFile = File.createTempFile("temp", ".pdf");
        try {
            file.transferTo(tempFile);
            // Ahora pasamos el tipoDocumentacion al servicio
            Res res = googleDriveService.uploadPdfToDrive(tempFile, idPersona, tipoDocumentacion, documentacionService);
            logger.info("PDF subido exitosamente: {}", res);
            return ResponseEntity.ok(res);
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
    @Operation(summary = "Actualizar la URL de la foto de perfil de una persona")
    @PostMapping("/updateProfilePictureUrl")
    public ResponseEntity<?> updateProfilePictureUrl(@RequestParam("idPersona") Long idPersona,
                                                     @RequestParam("urlFoto") String urlFoto) {
        try {
            personaService.actualizarUrlFoto(idPersona, urlFoto);
            logger.info("URL de la foto de perfil de la persona con ID {} actualizada a: {}", idPersona, urlFoto);
            return ResponseEntity.ok("URL de la foto de perfil actualizada exitosamente.");
        } catch (Exception e) {
            logger.error("Error al actualizar la URL de la foto de perfil de la persona con ID {}: {}", idPersona, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la URL de la foto de perfil: " + e.getMessage());
        }
    }

    @Operation(summary = "Subir un PDF de informe clínico y asociarlo a la persona")
    @PostMapping("/uploadInformeClinicoPdf")
    public ResponseEntity<?> handleInformeClinicoPdfUpload(
            @RequestParam("pdf") MultipartFile file,
            @RequestParam("idPersona") Long idPersona,
            @RequestParam("idInformeClinico") Long idInformeClinico) // Nuevo parámetro
            throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo está vacío.");
        }
        if (!file.getContentType().equals("application/pdf")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El archivo debe ser un PDF.");
        }
        File tempFile = File.createTempFile("temp", ".pdf");
        try {
            file.transferTo(tempFile);
            // Pasar el idInformeClinico al servicio
            Res res = googleDriveService.uploadInformeClinicoPdf(tempFile, idPersona, idInformeClinico, informeClinicoService);
            logger.info("PDF de informe clínico subido exitosamente: {}", res);
            return ResponseEntity.ok(res);
        } finally {
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}