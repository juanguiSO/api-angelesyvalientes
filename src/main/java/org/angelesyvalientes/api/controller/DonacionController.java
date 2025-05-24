package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.dto.DonacionResponseDTO;
import org.angelesyvalientes.api.persistence.entity.Donacion;
import org.angelesyvalientes.api.service.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // Para mapear a DTOs en el controlador si no lo hace el servicio

// Importar los DTOs de solicitud y respuesta
import org.angelesyvalientes.api.dto.DonacionRequestDTO;



/**
 * Controlador REST para la gestión de {@link Donacion}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar donaciones.
 * La API está etiquetada como "Donaciones" en la documentación de Swagger.
 */
@Tag(name = "Donaciones")
@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {
    private static final Logger logger = LoggerFactory.getLogger(DonacionController.class);
    private final DonacionService donacionService;

    /**
     * Constructor de la clase {@code DonacionController}.
     * Recibe una instancia de {@link DonacionService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con las donaciones.
     *
     * @param donacionService El servicio para la gestión de donaciones.
     */
    @Autowired
    public DonacionController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    /**
     * Endpoint para listar todas las donaciones.
     * Retorna una lista de todas las donaciones almacenadas en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de donaciones y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todas las Donaciones")
    @GetMapping
    public ResponseEntity<List<DonacionResponseDTO>> getAllDonaciones() {
        List<Donacion> donaciones = donacionService.getAllDonaciones();
        // Mapear entidades a DTOs de respuesta
        List<DonacionResponseDTO> donacionDTOs = donaciones.stream()
                .map(DonacionResponseDTO::fromEntity) // Asumiendo un método estático de mapeo en el DTO
                .collect(Collectors.toList());
        return new ResponseEntity<>(donacionDTOs, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener una donación por su ID.
     * Retorna una donación específica basada en el ID proporcionado en la ruta.
     *
     * @param id El identificador único de la donación a buscar.
     * @return Una respuesta {@link ResponseEntity} con la donación encontrada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la donación.
     */
    @Operation(summary = "Obtener una donación por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getDonacionById(@PathVariable Integer id) {
        try {
            Optional<Donacion> donacionOptional = donacionService.getDonacion(id);
            if (donacionOptional.isPresent()) {
                Donacion donacion = donacionOptional.get();
                // Mapear entidad a DTO de respuesta
                return new ResponseEntity<>(DonacionResponseDTO.fromEntity(donacion), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Donación con ID " + id + " no encontrada.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("DonacionController: Error al obtener donación con ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>("Error interno del servidor al obtener la donación.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para crear una nueva donación.
     * Recibe los datos de la nueva donación en el cuerpo de la petición y la guarda en la base de datos.
     *
     * @param donacionDTO El objeto {@link DonacionRequestDTO} con los datos de la nueva donación.
     * @return Una respuesta {@link ResponseEntity} con la donación creada y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear donación")
    @PostMapping
    public ResponseEntity<?> createDonacion(@RequestBody DonacionRequestDTO donacionDTO) { // Aceptar DTO de Request
        logger.info("DonacionController: Recibiendo solicitud para crear donación. Datos recibidos: {}", donacionDTO);
        try {
            // El servicio ahora aceptaría el DTO y se encargaría de cargar las entidades relacionadas
            Donacion nuevaDonacion = donacionService.createDonacion(donacionDTO);
            // Mapear la entidad creada a un DTO de respuesta antes de devolver
            return new ResponseEntity<>(DonacionResponseDTO.fromEntity(nuevaDonacion), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) { // Capturar si las entidades relacionadas no existen
            logger.error("DonacionController: Error al crear donación (entidad relacionada no encontrada): {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("DonacionController: Error inesperado al crear donación: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al crear la donación: " + e.getMessage());
        }
    }

    /**
     * Endpoint para actualizar la información de una donación existente.
     * Recibe el ID de la donación a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                  El identificador único de la donación a actualizar.
     * @param donacionDTO         El objeto {@link DonacionRequestDTO} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con la donación actualizada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la donación a actualizar.
     */
    @Operation(summary = "Actualizar una donación por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDonacion(@PathVariable Integer id, @RequestBody DonacionRequestDTO donacionDTO) { // Aceptar DTO de Request
        logger.info("DonacionController: Recibiendo solicitud para actualizar donación ID {}. Datos recibidos: {}", id, donacionDTO);
        try {
            // El servicio ahora aceptaría el DTO y se encargaría de cargar las entidades relacionadas
            Donacion donacionActualizada = donacionService.updateDonacion(id, donacionDTO);
            // Mapear la entidad actualizada a un DTO de respuesta antes de devolver
            return new ResponseEntity<>(DonacionResponseDTO.fromEntity(donacionActualizada), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error("DonacionController: Donación o entidad relacionada no encontrada al actualizar ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("DonacionController: Error inesperado al actualizar donación ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al actualizar la donación: " + e.getMessage());
        }
    }

    /**
     * Endpoint para eliminar una donación por su ID.
     * Recibe el ID de la donación a eliminar en la ruta.
     *
     * @param id El identificador único de la donación a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la donación a eliminar.
     */
    @Operation(summary = "Eliminar una donación por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDonacion(@PathVariable Integer id) {
        logger.info("DonacionController: Recibiendo solicitud para eliminar donación ID {}", id);
        try {
            donacionService.deleteDonacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            logger.error("DonacionController: Donación no encontrada para eliminar ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("DonacionController: Error inesperado al eliminar donación ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al eliminar la donación: " + e.getMessage());
        }
    }

    /**
     * Endpoint para obtener todas las donaciones de un Angel específico.
     *
     * GET /api/donaciones/persona/{idPersona}
     * Ejemplo: GET /api/donaciones/persona/1
     *
     * @param idPersona El ID de la persona.
     * @return ResponseEntity con la lista de donaciones o un estado 404 si la persona no existe.
     */
    @Operation(summary = "Listar donaciones por ID de persona")
    @GetMapping("/persona/{idPersona}")
    public ResponseEntity<?> getDonacionesByPersona(@PathVariable Integer idPersona) {
        try {
            List<Donacion> donaciones = donacionService.getDonacionesByPersonaId(idPersona);
            // Mapear entidades a DTOs de respuesta
            List<DonacionResponseDTO> donacionDTOs = donaciones.stream()
                    .map(DonacionResponseDTO::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(donacionDTOs);
        } catch (EntityNotFoundException e) {
            // Registrar el error para fines de depuración
            logger.warn("DonacionController: Error al obtener donaciones por persona ID {}: {}", idPersona, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // Devuelve 404 Not Found con mensaje de error
        } catch (Exception e) {
            logger.error("DonacionController: Error inesperado al obtener donaciones por persona ID {}: {}", idPersona, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al obtener las donaciones.");
        }
    }
}
