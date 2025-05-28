package org.angelesyvalientes.api.controller;

import com.google.api.services.drive.model.File;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.dto.InformeClinicoDTO;
import org.angelesyvalientes.api.dto.InformeClinicoListResponse;
import org.angelesyvalientes.api.persistence.entity.InformeClinico;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.service.GoogleDriveService;
import org.angelesyvalientes.api.service.InformeClinicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de {@link InformeClinico}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar informes clínicos.
 * La API está etiquetada como "Informes Clínicos" en la documentación de Swagger.
 */
@Tag(name = "Informes Clínicos")
@RestController
@RequestMapping("/api/informesclinicos")
public class InformeClinicoController {

    private final InformeClinicoService informeClinicoService;
    private static final Logger logger = LoggerFactory.getLogger(InformeClinicoController.class);
    private final GoogleDriveService googleDriveService;
    /**
     * Constructor de la clase {@code InformeClinicoController}.
     * Recibe una instancia de {@link InformeClinicoService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con los informes clínicos.
     *
     * @param informeClinicoService El servicio para la gestión de informes clínicos.
     */
    @Autowired
    public InformeClinicoController(InformeClinicoService informeClinicoService, GoogleDriveService googleDriveService) {
        this.informeClinicoService = informeClinicoService;
        this.googleDriveService = googleDriveService;
    }

    /**
     * Endpoint para listar todos los informes clínicos.
     * Retorna una lista de todos los informes clínicos almacenados en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de informes clínicos y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todos los Informes Clínicos")
    @GetMapping
    public ResponseEntity<List<InformeClinico>> getAllInformesClinicos() {
        List<InformeClinico> informesClinicos = informeClinicoService.getAllInformesClinicos();
        return new ResponseEntity<>(informesClinicos, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un informe clínico por su ID.
     * Retorna un informe clínico específico basado en el ID proporcionado en la ruta.
     *
     * @param id El identificador único del informe clínico a buscar.
     * @return Una respuesta {@link ResponseEntity} con el informe clínico encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el informe clínico.
     */
    @Operation(summary = "Obtener un informe clínico  por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<InformeClinico> getInformeClinicoById(@PathVariable Long id) {
        Optional<InformeClinico> informeClinico = informeClinicoService.getInformeClinico(id);
        return informeClinico.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para crear un nuevo informe clínico.
     * Recibe los datos del nuevo informe clínico en el cuerpo de la petición y lo guarda en la base de datos.
     *
     * @param informeClinicoDTO El objeto {@link InformeClinico} con los datos del nuevo informe clínico.
     * @return Una respuesta {@link ResponseEntity} con el informe clínico creado y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear informe clínico  ")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // Acepta multipart/form-data
    public ResponseEntity<InformeClinico> createInformeClinico(
            @RequestPart("informeClinico") InformeClinicoDTO informeClinicoDTO,
            @RequestPart("archivoInforme") MultipartFile archivoInforme) {

        logger.info("Content-Type del archivo recibido: {}", archivoInforme.getContentType());

        // Mapeo manual del DTO a la entidad
        Persona persona = new Persona();
        persona.setNmIdPersona(informeClinicoDTO.getPersona().getNmIdPersona());

        InformeClinico informeClinico = new InformeClinico();
        informeClinico.setPersona(persona);
        informeClinico.setFecha(informeClinicoDTO.getFecha());
        informeClinico.setTipoInforme(informeClinicoDTO.getTipoInforme());
        informeClinico.setProfesional(informeClinicoDTO.getProfesional());
        informeClinico.setUrlPdf(informeClinicoDTO.getUrlPdf());

        InformeClinico nuevoInformeClinico = informeClinicoService.createInformeClinico(informeClinico, archivoInforme);
        if (nuevoInformeClinico != null) {
            return new ResponseEntity<>(nuevoInformeClinico, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para actualizar la información de un informe clínico existente.
     * Recibe el ID del informe clínico a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                      El identificador único del informe clínico a actualizar.
     * @param informeClinicoActualizado El objeto {@link InformeClinico} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con el informe clínico actualizado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el informe clínico a actualizar.
     */
    @Operation(summary = "Actualizar un informe clínico por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<InformeClinico> updateInformeClinico(@PathVariable Long id, @RequestBody InformeClinico informeClinicoActualizado) {
        try {
            InformeClinico informeClinico = informeClinicoService.updateInformeClinico(id, informeClinicoActualizado);
            return new ResponseEntity<>(informeClinico, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar un informe clínico por su ID.
     * Recibe el ID del informe clínico a eliminar en la ruta.
     *
     * @param id El identificador único del informe clínico a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el informe clínico a eliminar.
     */
    @Operation(summary = "Eliminar una informe clínico por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInformeClinico(@PathVariable Long id) {
        try {
            informeClinicoService.deleteInformeClinico(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Listar todas los estudios de una persona por su ID")
    @GetMapping("/persona/{personaId}")

    public ResponseEntity<List<InformeClinicoListResponse>> getInformesClinicosPorPersona(@PathVariable Long personaId) {
        List<InformeClinico> informes = informeClinicoService.getInformesClinicosPorPersona(personaId);
        List<InformeClinicoListResponse> response = informes.stream()
                .map(informe -> new InformeClinicoListResponse(
                        informe.getIdInformeClinico(),
                        informe.getFecha(),
                        informe.getTipoInforme(),
                        informe.getProfesional(),
                        informe.getUrlPdf()
                ))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint para descargar un informe clínico PDF de Google Drive por el ID del Informe Clínico en tu BD.
     *
     * @param idInformeClinico El ID del InformeClinico en la base de datos (NO el ID de Google Drive).
     * @return Una respuesta {@link ResponseEntity} con el archivo PDF y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si el informe clínico no existe o no tiene un PDF asociado,
     * o estado HTTP 500 (INTERNAL_SERVER_ERROR) si ocurre un error en la descarga.
     */
    @Operation(summary = "Descargar un informe clínico PDF desde Google Drive por el ID del Informe Clínico")
    @GetMapping("/{idInformeClinico}/download")
    public ResponseEntity<byte[]> downloadInformeClinicoPdf(@PathVariable Long idInformeClinico) {
        try {
            // Primero, obtenemos el InformeClinico de nuestra BD para sacar el Google Drive File ID
            Optional<InformeClinico> informeClinicoOptional = informeClinicoService.getInformeClinico(idInformeClinico);
            if (!informeClinicoOptional.isPresent()) {
                logger.warn("No se encontró informe clínico con ID: {}", idInformeClinico);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            InformeClinico informeClinico = informeClinicoOptional.get();
            String googleDriveFileId = informeClinico.getUrlPdf();

            if (googleDriveFileId == null || googleDriveFileId.isEmpty()) {
                logger.warn("El informe clínico con ID {} no tiene un PDF asociado en Google Drive.", idInformeClinico);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Opcional: Obtener metadatos para el nombre y tipo MIME
            File fileMetadata = googleDriveService.getFileMetadata(googleDriveFileId);
            String fileName = fileMetadata.getName();
            String mimeType = fileMetadata.getMimeType();

            // Descargar el archivo
            byte[] fileContent = googleDriveService.downloadFile(googleDriveFileId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeType != null ? mimeType : "application/octet-stream"));
            headers.setContentDispositionFormData("attachment", fileName != null ? fileName : "informe_clinico.pdf"); // Nombre sugerido para la descarga
            headers.setContentLength(fileContent.length);

            logger.info("PDF de informe clínico con ID {} descargado exitosamente.", idInformeClinico);
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

        } catch (RuntimeException e) {
            logger.error("Error al buscar informe clínico o su PDF: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (GeneralSecurityException | IOException e) {
            logger.error("Error al descargar el PDF de Google Drive para el informe clínico con ID {}: {}", idInformeClinico, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
