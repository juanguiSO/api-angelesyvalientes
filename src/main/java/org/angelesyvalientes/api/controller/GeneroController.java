package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Genero;
import org.angelesyvalientes.api.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Genero}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar géneros.
 * La API está etiquetada como "Géneros" en la documentación de Swagger.
 */
@Tag(name = "Géneros")
@RestController
@RequestMapping("/api/generos")
public class GeneroController {

    private final GeneroService generoService;

    /**
     * Constructor de la clase {@code GeneroController}.
     * Recibe una instancia de {@link GeneroService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con los géneros.
     *
     * @param generoService El servicio para la gestión de géneros.
     */
    @Autowired
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    /**
     * Endpoint para listar todos los géneros.
     * Retorna una lista de todos los géneros almacenados en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de géneros y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar géneros")
    @GetMapping
    public ResponseEntity<List<Genero>> getGeneros() {
        List<Genero> generos = generoService.getGeneros();
        return new ResponseEntity<>(generos, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un género por su ID.
     * Retorna un género específico basado en el ID proporcionado en la ruta.
     *
     * @param id El identificador único del género a buscar.
     * @return Una respuesta {@link ResponseEntity} con el género encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el género.
     */
    @Operation(summary = "Obtener un género por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Genero> getGenero(@PathVariable Long id) {
        Optional<Genero> genero = generoService.getGenero(id);

        if (genero.isPresent()) {
            return new ResponseEntity<>(genero.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para crear un nuevo género.
     * Recibe los datos del nuevo género en el cuerpo de la petición y lo guarda en la base de datos.
     *
     * @param genero El objeto {@link Genero} con los datos del nuevo género.
     * @return Una respuesta {@link ResponseEntity} con el género creado y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear un nuevo género")
    @PostMapping
    public ResponseEntity<Genero> saveGenero(@RequestBody Genero genero) {
        Genero nuevoGenero = generoService.saveGenero(genero);
        return new ResponseEntity<>(nuevoGenero, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de un género existente.
     * Recibe el ID del género a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id             El identificador único del género a actualizar.
     * @param generoActualizado El objeto {@link Genero} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con el género actualizado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el género a actualizar.
     */
    @Operation(summary = "Actualizar un género existente")
    @PutMapping("/{id}")
    public ResponseEntity<Genero> updateGenero(@PathVariable Long id, @RequestBody Genero generoActualizado) {
        try {
            Genero genero = generoService.updateGenero(id, generoActualizado);
            return new ResponseEntity<>(genero, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar un género por su ID.
     * Recibe el ID del género a eliminar en la ruta.
     *
     * @param id El identificador único del género a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el género a eliminar.
     */
    @Operation(summary = "Eliminar un género por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenero(@PathVariable Long id) {
        try {
            generoService.deleteGenero(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
