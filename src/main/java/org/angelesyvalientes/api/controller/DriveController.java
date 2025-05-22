package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.dto.ActualizarContrasenaRequestDTO; // No usada en este controlador
import org.angelesyvalientes.api.dto.UsuarioRequestDTO; // No usada en este controlador
import org.angelesyvalientes.api.persistence.entity.Angel; // No usada en este controlador
import org.angelesyvalientes.api.persistence.entity.TipoDonacion; // No usada en este controlador
import org.angelesyvalientes.api.persistence.entity.Persona; // No usada directamente aquí, pero sí en servicios
import org.angelesyvalientes.api.persistence.entity.Rol; // No usada en este controlador
import org.angelesyvalientes.api.persistence.entity.Usuario; // No usada en este controlador
import org.angelesyvalientes.api.persistence.repository.PersonaRepository; // No usada directamente aquí
import org.angelesyvalientes.api.persistence.repository.RolRepository; // No usada en este controlador
import org.angelesyvalientes.api.persistence.repository.UsuarioRepository; // No usada en este controlador
import org.angelesyvalientes.api.security.Res;
import org.angelesyvalientes.api.service.CodigoVerificacionService; // No usada en este controlador
import org.angelesyvalientes.api.service.DocumentacionService;
import org.angelesyvalientes.api.service.EmailService; // No usada en este controlador
import org.angelesyvalientes.api.service.GoogleDriveService;
import org.angelesyvalientes.api.service.InformeClinicoService;
import org.angelesyvalientes.api.service.PersonaService;
import org.angelesyvalientes.api.service.TipoDonacionService; // No usada en este controlador
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*; // No usada en este controlador
import org.springframework.security.core.Authentication; // No usada en este controlador
import org.springframework.security.core.userdetails.UserDetails; // No usada en este controlador
import org.springframework.security.crypto.password.PasswordEncoder; // No usada en este controlador
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException; // No usada en este controlador
import java.io.File;
import java.io.IOException;
import java.nio.file.Files; // Necesario para Files.createTempFile
import java.nio.file.Path; // Necesario para Path
import java.security.GeneralSecurityException;
import java.time.LocalDate; // No usada en este controlador
import java.util.List; // No usada en este controlador
import java.util.Map; // No usada en este controlador
import java.util.Optional; // No usada en este controlador
import java.util.stream.Collectors; // No usada en este controlador

/**
 * Controlador REST para la carga de archivos a Google Drive.
 */
@Tag(name = "Carga a Drive")
@RestController
@RequestMapping("/api")
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
                    // CORRECCIÓN: Convertir Long a Integer para personaService.actualizarUrlFoto()
                    personaService.actualizarUrlFoto(idPersona.intValue(), res.getUrl());
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
            // CORRECCIÓN: Convertir Long a Integer para personaService.actualizarUrlFoto()
            personaService.actualizarUrlFoto(idPersona.intValue(), urlFoto);
            logger.info("URL de la foto de perfil de la persona con ID {} actualizada a: {}", idPersona, urlFoto);
            return ResponseEntity.ok("URL de la foto de perfil actualizada exitosamente.");
        } catch (Exception e) {
            logger.error("Error al actualizar la URL de la foto de perfil de la persona con ID {}: {}", idPersona, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la URL de la foto de perfil: " + e.getMessage());
        }
    }
}
