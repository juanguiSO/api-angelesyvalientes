package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.service.PersonaService;
import org.angelesyvalientes.api.service.GoogleDriveService; // Importar el servicio de Google Drive
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders; // Necesario para los encabezados de respuesta
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType; // Necesario para los tipos MIME
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ByteArrayResource; // Necesario para devolver el array de bytes

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Persona}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar personas.
 * La API está etiquetada como "Personas" en la documentación de Swagger.
 */
@Tag(name = "Personas")
@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;
    private final GoogleDriveService googleDriveService; // Inyectar GoogleDriveService
    private static final Logger logger = LoggerFactory.getLogger(PersonaController.class);

    /**
     * Constructor de la clase {@code PersonaController}.
     * Recibe instancias de {@link PersonaService} y {@link GoogleDriveService}
     * a través de la inyección de dependencias para manejar la lógica de negocio
     * relacionada con las personas y la interacción con Google Drive.
     *
     * @param personaService El servicio para la gestión de personas.
     * @param googleDriveService El servicio para la interacción con Google Drive.
     */
    @Autowired
    public PersonaController(PersonaService personaService, GoogleDriveService googleDriveService) {
        this.personaService = personaService;
        this.googleDriveService = googleDriveService;
    }

    /**
     * Endpoint para listar todas las personas.
     * Retorna una lista de todas las personas almacenadas en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de personas y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todas las personas")
    @GetMapping
    public ResponseEntity<List<Persona>> getAllPersonas() {
        List<Persona> personas = personaService.getAllPersonas();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener una persona por su ID.
     * Retorna una persona específica basada en el ID proporcionado en la ruta.
     *
     * @param id El identificador único de la persona a buscar.
     * @return Una respuesta {@link ResponseEntity} con la persona encontrada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la persona.
     */
    @Operation(summary = "Obtener una persona por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        Optional<Persona> persona = personaService.getPersona(id.intValue());
        return persona.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para crear una nueva persona.
     * Recibe los datos de la nueva persona en el cuerpo de la petición y la guarda en la base de datos.
     *
     * @param persona El objeto {@link Persona} con los datos de la nueva persona.
     * @return Una respuesta {@link ResponseEntity} con la persona creada y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear persona")
    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        Persona nuevaPersona = personaService.createPersona(persona);
        return new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de una persona existente.
     * Recibe el ID de la persona a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                  El identificador único de la persona a actualizar.
     * @param personaActualizada El objeto {@link Persona} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con la persona actualizada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la persona a actualizar.
     */
    @Operation(summary = "Actualizar una persona por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        logger.info("RECIBIDA Petición PUT para actualizar Persona ID: {}", id);
        try {
            logger.debug("Llamando a personaService.updatePersona para ID: {}", id);
            Persona personaGuardada = personaService.updatePersona(id.intValue(), personaActualizada);
            logger.info("TERMINADA Petición PUT para actualizar Persona ID: {}. Nueva versión: {}", id, personaGuardada.getVersion());
            return new ResponseEntity<>(personaGuardada, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.warn("Petición PUT para actualizar Persona ID: {} fallida. Causa: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar una persona por su ID.
     * Recibe el ID de la persona a eliminar en la ruta.
     *
     * @param id El identificador único de la persona a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la persona a eliminar.
     */
    @Operation(summary = "Eliminar una persona por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        try {
            personaService.deletePersona(id.intValue());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para descargar la foto de perfil de una persona desde Google Drive.
     * La URL de la foto de perfil se obtiene de la entidad Persona.
     *
     * @param id El ID de la persona cuya foto de perfil se desea descargar.
     * @return ResponseEntity con el contenido de la imagen y los encabezados apropiados,
     * o un estado de error si la persona no se encuentra, la URL de la foto no existe,
     * o hay un problema al descargar el archivo.
     */
    @Operation(summary = "Descargar foto de perfil de una persona")
    @GetMapping("/{id}/PersonaFoto") // Endpoint específico para la foto de perfil
    public ResponseEntity<ByteArrayResource> downloadPersonaFoto(@PathVariable Long id) {
        logger.info("RECIBIDA Petición GET para descargar foto de perfil de Persona ID: {}", id);
        try {
            // 1. Obtener la persona para obtener la URL de la foto de perfil
            Optional<Persona> personaOptional = personaService.getPersona(id.intValue());
            if (personaOptional.isEmpty()) {
                logger.warn("Persona con ID {} no encontrada para descargar foto.", id);
                return ResponseEntity.notFound().build(); // 404 Not Found
            }
            Persona persona = personaOptional.get();

            String fotoPerfilUrl = persona.getUrlFoto(); // Asumiendo que este es el campo de la URL
            if (fotoPerfilUrl == null || fotoPerfilUrl.trim().isEmpty()) {
                logger.warn("La persona con ID {} no tiene URL de foto de perfil registrada.", id);
                return ResponseEntity.badRequest().body(null); // 400 Bad Request si no hay URL
            }

            // 2. Extraer el ID del archivo de Google Drive de la URL
            String fileId = googleDriveService.extractFileIdFromUrl(fotoPerfilUrl);
            if (fileId == null || fileId.isEmpty()) {
                logger.error("No se pudo extraer el ID del archivo de Google Drive de la URL: {}", fotoPerfilUrl);
                return ResponseEntity.badRequest().body(null); // 400 Bad Request si el ID no es válido
            }

            // 3. Obtener metadatos del archivo (nombre y tipo MIME)
            com.google.api.services.drive.model.File fileMetadata = googleDriveService.getFileMetadata(fileId);
            if (fileMetadata == null) {
                logger.warn("Metadatos de la foto de perfil no encontrados para el ID: {}", fileId);
                return ResponseEntity.notFound().build(); // 404 Not Found si el archivo no existe o no hay permisos
            }

            // 4. Descargar el contenido del archivo
            byte[] fileContent = googleDriveService.downloadFile(fileId);
            if (fileContent == null) {
                logger.error("Contenido de la foto de perfil nulo para el ID: {}", fileId);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
            }

            // 5. Preparar la respuesta HTTP
            String fileName = fileMetadata.getName();
            String mimeType = fileMetadata.getMimeType() != null ? fileMetadata.getMimeType() : MediaType.APPLICATION_OCTET_STREAM_VALUE;

            ByteArrayResource resource = new ByteArrayResource(fileContent);

            logger.info("Foto de perfil de Persona ID {} descargada exitosamente. Nombre: {}, Tipo: {}", id, fileName, mimeType);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.parseMediaType(mimeType))
                    .contentLength(fileContent.length)
                    .body(resource);

        } catch (GeneralSecurityException e) {
            logger.error("Error de seguridad/autenticación al descargar la foto de perfil para Persona ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            logger.error("Error de E/S al descargar la foto de perfil para Persona ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            logger.error("Error inesperado al descargar la foto de perfil para Persona ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
