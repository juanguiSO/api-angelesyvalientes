package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Educacion;
import org.angelesyvalientes.api.service.EducacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Educacion}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar información educativa.
 * La API está etiquetada como "Educaciones" en la documentación de Swagger.
 */
@Tag(name = "Educaciones")
@RestController
@RequestMapping("/api/educaciones")
public class EducacionController {

    private final EducacionService educacionService;

    /**
     * Constructor de la clase {@code EducacionController}.
     * Recibe una instancia de {@link EducacionService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con la información educativa.
     *
     * @param educacionService El servicio para la gestión de la información educativa.
     */
    @Autowired
    public EducacionController(EducacionService educacionService) {
        this.educacionService = educacionService;
    }

    /**
     * Endpoint para listar toda la información educativa.
     * Retorna una lista de toda la información educativa almacenada en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de información educativa y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todos las Educaciones")
    @GetMapping
    public ResponseEntity<List<Educacion>> getAllEducaciones() {
        List<Educacion> educaciones = educacionService.getAllEducaciones();
        return new ResponseEntity<>(educaciones, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener información educativa por su ID.
     * Retorna una entrada de información educativa específica basada en el ID proporcionado en la ruta.
     *
     * @param id El identificador único de la información educativa a buscar.
     * @return Una respuesta {@link ResponseEntity} con la información educativa encontrada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la información educativa.
     */
    @Operation(summary = "Obtener una Educación por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Educacion> getEducacionById(@PathVariable Long id) {
        Optional<Educacion> educacion = educacionService.getEducacion(id);
        return educacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para crear una nueva entrada de información educativa.
     * Recibe los datos de la nueva información educativa en el cuerpo de la petición y la guarda en la base de datos.
     *
     * @param educacion El objeto {@link Educacion} con los datos de la nueva información educativa.
     * @return Una respuesta {@link ResponseEntity} con la información educativa creada y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear una Educación ")
    @PostMapping
    public ResponseEntity<Educacion> createEducacion(@RequestBody Educacion educacion) {
        Educacion nuevaEducacion = educacionService.createEducacion(educacion);
        return new ResponseEntity<>(nuevaEducacion, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de una entrada educativa existente.
     * Recibe el ID de la información educativa a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                  El identificador único de la información educativa a actualizar.
     * @param educacionActualizada El objeto {@link Educacion} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con la información educativa actualizada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la información educativa a actualizar.
     */
    @Operation(summary = "Actualizar una Educación por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Educacion> updateEducacion(@PathVariable Long id, @RequestBody Educacion educacionActualizada) {
        try {
            Educacion educacion = educacionService.updateEducacion(id, educacionActualizada);
            return new ResponseEntity<>(educacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar información educativa por su ID.
     * Recibe el ID de la información educativa a eliminar en la ruta.
     *
     * @param id El identificador único de la información educativa a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la información educativa a eliminar.
     */
    @Operation(summary = "Eliminar una Educación por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducacion(@PathVariable Long id) {
        try {
            educacionService.deleteEducacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}