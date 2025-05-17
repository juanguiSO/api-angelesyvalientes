package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.dto.DocumentacionDTO;
import org.angelesyvalientes.api.dto.DocumentacionListResponse;
import org.angelesyvalientes.api.persistence.entity.Documentacion;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.service.DocumentacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST para la gestión de {@link Documentacion}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar documentación.
 * La API está etiquetada como "Documentaciones" en la documentación de Swagger.
 */
@Tag(name = "Documentaciones")
@RestController
@RequestMapping("/api/documentaciones")
public class DocumentacionController {
    private static final Logger logger = LoggerFactory.getLogger(InformeClinicoController.class);
    private final DocumentacionService documentacionService;

    /**
     * Constructor de la clase {@code DocumentacionController}.
     * Recibe una instancia de {@link DocumentacionService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con la documentación.
     *
     * @param documentacionService El servicio para la gestión de documentación.
     */
    @Autowired
    public DocumentacionController(DocumentacionService documentacionService) {
        this.documentacionService = documentacionService;
    }

    /**
     * Endpoint para listar toda la documentación.
     * Retorna una lista de toda la documentación almacenada en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de documentación y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todas las documentaciones")
    @GetMapping
    public ResponseEntity<List<DocumentacionListResponse>> getAllDocumentaciones() {
        List<Documentacion> documentaciones = documentacionService.getAllDocumentaciones();
        List<DocumentacionListResponse> response = documentaciones.stream()
                .map(doc -> {
                    DocumentacionListResponse dto = new DocumentacionListResponse();
                    dto.setIdDocumentacion(doc.getIdDocumentacion());
                    dto.setTipoDocumentacion(doc.getTipoDocumentacion());
                    dto.setUrlPdf(doc.getUrlPdf());
                    dto.setFecha(doc.getFecha());
                    if (doc.getPersona() != null) {
                        dto.setPersonaId(Long.valueOf(doc.getPersona().getNmIdPersona())); // <--- Accede solo al ID
                    }
                    return dto;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener una documentación por su ID.
     * Retorna una documentación específica basada en el ID proporcionado en la ruta.
     *
     * @param id El identificador único de la documentación a buscar.
     * @return Una respuesta {@link ResponseEntity} con la documentación encontrada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la documentación.
     */
    @Operation(summary = "Obtener una Lista de documentacion por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Documentacion> getDocumentacionById(@PathVariable Long id) {
        Optional<Documentacion> documentacion = documentacionService.getDocumentacion(id);
        return documentacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para crear un nuevo Documento.
     * Recibe los datos de la nueva documentación en el cuerpo de la petición y la guarda en la base de datos.
     *
     * @param documentacionDTO El objeto {@link Documentacion} con los datos de la nueva documentación.
     * @return Una respuesta {@link ResponseEntity} con la documentación creada y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear un documento")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // Acepta multipart/form-data
    public ResponseEntity<Documentacion> createDocumentacion(
            @RequestPart("documentacion") DocumentacionDTO documentacionDTO,
            @RequestPart("archivoInforme") MultipartFile archivoInforme){
        logger.info("Content-Type del archivo recibido: {}", archivoInforme.getContentType());

        // Mapeo manual del DTO a la entidad
        Persona persona = new Persona();
        persona.setNmIdPersona(documentacionDTO.getPersona().getNmIdPersona());

        Documentacion documentacion = new Documentacion();
        documentacion.setPersona(persona);
        documentacion.setFecha(documentacionDTO.getFecha());
        documentacion.setUrlPdf(documentacionDTO.getUrlPdf());
        documentacion.setTipoDocumentacion(documentacionDTO.getTipoDocumentacion());


        Documentacion nuevaDocumentacion = documentacionService.createDocumentacion(documentacion, archivoInforme);
        if(nuevaDocumentacion!= null){
            return new ResponseEntity<>(nuevaDocumentacion, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Endpoint para actualizar la información de una documentación existente.
     * Recibe el ID de la documentación a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                      El identificador único de la documentación a actualizar.
     * @param documentacionActualizada El objeto {@link Documentacion} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con la documentación actualizada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la documentación a actualizar.
     */
    @Operation(summary = "Actualizar una Lista de documentacion  por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Documentacion> updateDocumentacion(@PathVariable Long id, @RequestBody Documentacion documentacionActualizada) {
        try {
            Documentacion documentacion = documentacionService.updateDocumentacion(id, documentacionActualizada);
            return new ResponseEntity<>(documentacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar una documentación por su ID.
     * Recibe el ID de la documentación a eliminar en la ruta.
     *
     * @param id El identificador único de la documentación a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la documentación a eliminar.
     */
    @Operation(summary = "Eliminar una Lista de documentacion por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentacion(@PathVariable Long id) {
        try {
            documentacionService.deleteDocumentacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para listar todos los documentos de una persona específica.
     *
     * @param personaId El ID de la persona de la cual se desean obtener los documentos.
     * @return Una respuesta {@link ResponseEntity} con la lista de documentos de la persona y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentran documentos para esa persona (o si la persona no existe,
     * aunque actualmente no validamos la existencia de la persona aquí).
     */
    @Operation(summary = "Listar todos los documentos de una persona por su ID")
    @GetMapping("/persona/{personaId}")
    public ResponseEntity<List<DocumentacionListResponse>> getDocumentacionesPorPersona(@PathVariable Integer personaId) {
        List<Documentacion> documentaciones = documentacionService.getDocumentacionesPorPersona(personaId);
        List<DocumentacionListResponse> response = documentaciones.stream()
                .map(doc -> {
                    DocumentacionListResponse dto = new DocumentacionListResponse();
                    dto.setIdDocumentacion(doc.getIdDocumentacion());
                    dto.setTipoDocumentacion(doc.getTipoDocumentacion());
                    dto.setUrlPdf(doc.getUrlPdf());
                    dto.setFecha(doc.getFecha());
                    if (doc.getPersona() != null) {
                        dto.setPersonaId(Long.valueOf(doc.getPersona().getNmIdPersona()));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}