package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Acompanante;
import org.angelesyvalientes.api.service.AcompananteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Acompanante}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar acompañantes.
 * La API está etiquetada como "Acompañantes" en la documentación de Swagger.
 */
@Tag(name = "Acompañantes")
@RestController
@RequestMapping("/api/acompanantes")
public class AcompananteController {

    private final AcompananteService acompananteService;

    /**
     * Constructor de la clase {@code AcompananteController}.
     * Recibe una instancia de {@link AcompananteService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con los acompañantes.
     *
     * @param acompananteService El servicio para la gestión de acompañantes.
     */
    @Autowired
    public AcompananteController(AcompananteService acompananteService) {
        this.acompananteService = acompananteService;
    }

    /**
     * Endpoint para listar todos los acompañantes.
     * Retorna una lista de todos los acompañantes almacenados en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de acompañantes y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todos los acompañantes")
    @GetMapping
    public ResponseEntity<List<Acompanante>> getAllAcompanantes() {
        List<Acompanante> acompanantes = acompananteService.getAllAcompanantes();
        return new ResponseEntity<>(acompanantes, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un acompañante por su ID.
     * Retorna un acompañante específico basado en el ID proporcionado en la ruta.
     *
     * @param id El identificador único del acompañante a buscar.
     * @return Una respuesta {@link ResponseEntity} con el acompañante encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el acompañante.
     */
    @Operation(summary = "Obtener un acompañante por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Acompanante> getAcompananteById(@PathVariable Long id) {
        Optional<Acompanante> acompanante = acompananteService.getAcompanante(id);
        return acompanante.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para crear un nuevo acompañante.
     * Recibe los datos del nuevo acompañante en el cuerpo de la petición y lo guarda en la base de datos.
     *
     * @param acompanante El objeto {@link Acompanante} con los datos del nuevo acompañante.
     * @return Una respuesta {@link ResponseEntity} con el acompañante creado y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear acompañnte")
    @PostMapping
    public ResponseEntity<Acompanante> createAcompanante(@RequestBody Acompanante acompanante) {
        Acompanante nuevoAcompanante = acompananteService.createAcompanante(acompanante);
        return new ResponseEntity<>(nuevoAcompanante, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de un acompañante existente.
     * Recibe el ID del acompañante a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                  El identificador único del acompañante a actualizar.
     * @param acompananteActualizado El objeto {@link Acompanante} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con el acompañante actualizado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el acompañante a actualizar.
     */
    @Operation(summary = "Actualizar una acompañante por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Acompanante> updateAcompanante(@PathVariable Long id, @RequestBody Acompanante acompananteActualizado) {
        try {
            Acompanante acompanante = acompananteService.updateAcompanante(id, acompananteActualizado);
            return new ResponseEntity<>(acompanante, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar un acompañante por su ID.
     * Recibe el ID del acompañante a eliminar en la ruta.
     *
     * @param id El identificador único del acompañante a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el acompañante a eliminar.
     */
    @Operation(summary = "Eliminar una acompañante por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcompanante(@PathVariable Long id) {
        try {
            acompananteService.deleteAcompanante(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}