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

    @Autowired
    public EducacionController(EducacionService educacionService) {
        this.educacionService = educacionService;
    }

    @Operation(summary = "Listar todos los Estudios")
    @GetMapping
    public ResponseEntity<List<Educacion>> getAllEducaciones() {
        List<Educacion> educaciones = educacionService.getAllEducaciones();
        return new ResponseEntity<>(educaciones, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un estudio por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Educacion> getEducacionById(@PathVariable Long id) {
        Optional<Educacion> educacion = educacionService.getEducacion(id);
        return educacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Crear un Estudio ")
    @PostMapping
    public ResponseEntity<Educacion> createEducacion(@RequestBody Educacion educacion) {
        try {
            Educacion nuevaEducacion = educacionService.createEducacion(educacion);
            return new ResponseEntity<>(nuevaEducacion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Retorna solo el estado, o podrías crear un objeto de error
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna solo el estado, o podrías crear un objeto de error
        }
    }

    @Operation(summary = "Actualizar un Eestudio por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Educacion> updateEducacion(@PathVariable Long id, @RequestBody Educacion educacionActualizada) {
        try {
            Educacion educacion = educacionService.updateEducacion(id, educacionActualizada);
            return new ResponseEntity<>(educacion, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Retorna solo el estado
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna solo el estado
        }
    }

    @Operation(summary = "Eliminar un estudio por su ID")
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