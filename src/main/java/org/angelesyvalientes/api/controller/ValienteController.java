package org.angelesyvalientes.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper; // Importa ObjectMapper
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.DetallesValienteDTO.DetallesValienteDTO;
import org.angelesyvalientes.api.DetallesValienteDTO.ValienteRequest;
import org.angelesyvalientes.api.persistence.entity.Valiente;
//import org.angelesyvalientes.api.service.GoogleDriveService; // Importa GoogleDriveService
import org.angelesyvalientes.api.service.ValienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // Importa MultipartFile

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Valiente}.
 * Expone endpoints para listar, obtener, actualizar y eliminar valientes,
 * así como para asignarles una ficha.
 * La API está etiquetada como "Valientes" en la documentación de Swagger.
 */
@Tag(name = "Valientes")
@RestController
@RequestMapping("/api/valientes")
public class ValienteController {

    private final ValienteService valienteService;
    //private final GoogleDriveService googleDriveService; // Inyecta GoogleDriveService (Comentado en el código original)
    private final ObjectMapper objectMapper; // Inyecta ObjectMapper

    /**
     * Constructor de la clase {@code ValienteController}.
     * Recibe instancias de {@link ValienteService} y {@link ObjectMapper}
     * a través de la inyección de dependencias.
     *
     * @param valienteService El servicio para la gestión de valientes.
     * @param objectMapper    El ObjectMapper para la manipulación de objetos JSON.
     */
    @Autowired
    public ValienteController(ValienteService valienteService, ObjectMapper objectMapper) {
        this.valienteService = valienteService;
        this.objectMapper = objectMapper;
    }

    /**
     * Endpoint para listar todos los valientes.
     * Retorna una lista de todos los valientes almacenados en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de valientes y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todos los valientes")
    @GetMapping
    public ResponseEntity<List<Valiente>> getValientes() {
        List<Valiente> valiente = valienteService.getValientes();
        return new ResponseEntity<>(valiente, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un valiente por su ID.
     * Retorna un valiente específico basado en el ID proporcionado en la ruta.
     *
     * @param id El identificador único del valiente a buscar.
     * @return Una respuesta {@link ResponseEntity} con el valiente encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el valiente.
     */
    @Operation(summary = "Obtener valiente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Valiente> getValiente(@PathVariable Long id) {
        Optional<Valiente> valiente = valienteService.getValiente(id);

        if (valiente.isPresent()) {
            return new ResponseEntity<>(valiente.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para actualizar la información de un valiente existente.
     * Recibe el ID del valiente a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                  El identificador único del valiente a actualizar.
     * @param valienteActualizada El objeto {@link Valiente} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con el valiente actualizado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el valiente a actualizar.
     */
    @Operation(summary = "Actualizar valiente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Valiente> updateValiente(@PathVariable Long id, @RequestBody Valiente valienteActualizada) {
        try {
            Valiente valiente = valienteService.actualizarValiente(id, valienteActualizada);
            return new ResponseEntity<>(valiente, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar un valiente por su ID.
     * Recibe el ID del valiente a eliminar en la ruta.
     *
     * @param id El identificador único del valiente a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el valiente a eliminar.
     */
    @Operation(summary = "Eliminar valiente por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValiente(@PathVariable Long id) {
        try {
            valienteService.deleteValiente(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para asignar una ficha a un valiente.
     * Recibe el ID del valiente y el ID de la ficha a asignar en la ruta.
     *
     * @param id      El identificador único del valiente al que se asignará la ficha.
     * @param idFicha El identificador único de la ficha a asignar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 201 (CREATED) si la asignación fue exitosa.
     * Puede devolver otros códigos de estado en caso de error (por ejemplo, 404 si no se encuentran el valiente o la ficha).
     * Nota: La implementación actual en el código original no maneja posibles errores de no encontrar el valiente o la ficha.
     */
    @Operation(summary = "Asignar ficha al Valiente")
    @GetMapping("/{id}/asignarFicha/{idFicha}")
    public ResponseEntity<Valiente> asignarFicha(@PathVariable int id, @PathVariable int idFicha) {
        valienteService.asignarFicha(id, idFicha);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Endpoint para la segunda etapa de creación de un Valiente: asociar los detalles
     * específicos del Valiente a una Persona existente.
     *
     * @param request Un objeto JSON que debe contener el ID de la Persona
     * (`idPersona`) y los detalles específicos del Valiente.
     * @return ResponseEntity con el Valiente creado si la Persona existe,
     * o un error si la Persona no se encuentra.
     */
    @PostMapping("/segunda-etapa")
    public ResponseEntity<?> crearValienteSegundaEtapa(@RequestBody DetallesValienteRequest request) {
        Optional<Valiente> valienteCreado = valienteService.crearValienteSegundaEtapa(request.getIdPersona(), request.getDetallesValiente());

        if (valienteCreado.isPresent()) {
            return new ResponseEntity<>(valienteCreado.get(), HttpStatus.CREATED);
        } else {
            String errorMessage = String.format(
                    "No se encontró la Persona con ID: %d. Datos de la petición: %s",
                    request.getIdPersona(),
                    request.toString() // Asumiendo que DetallesValienteRequest tiene un toString() útil
            );
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Clase interna (o podrías tener una clase DTO separada) para manejar la
     * recepción de los datos de la segunda etapa de creación del Valiente.
     */
    public static class DetallesValienteRequest {
        private Long idPersona;
        private Valiente detallesValiente;

        // Getters y setters
        public Long getIdPersona() {
            return idPersona;
        }

        public void setIdPersona(Long idPersona) {
            this.idPersona = idPersona;
        }

        public Valiente getDetallesValiente() {
            return detallesValiente;
        }

        public void setDetallesValiente(Valiente detallesValiente) {
            this.detallesValiente = detallesValiente;
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearValiente(
            @RequestBody DetallesValienteDTO request
    ) {
        try {
            Valiente valienteCreado = valienteService.crearValiente(
                    request.idPersona(),
                    request
            );
            return new ResponseEntity<>(valienteCreado, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}