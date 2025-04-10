package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Programa;
import org.angelesyvalientes.api.service.ProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Programa}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar programas.
 * La API está etiquetada como "Programas" en la documentación de Swagger.
 */
@Tag(name = "Programas")
@RestController
@RequestMapping("/api/programa")
public class ProgramaController {

    private final ProgramaService programaService;

    /**
     * Constructor de la clase {@code ProgramaController}.
     * Recibe una instancia de {@link ProgramaService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con los programas.
     *
     * @param programaService El servicio para la gestión de programas.
     */
    @Autowired
    public ProgramaController(ProgramaService programaService) {
        this.programaService = programaService;
    }

    /**
     * Endpoint para listar todos los programas.
     * Retorna una lista de todos los programas almacenados en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de programas y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar programas")
    @GetMapping
    public ResponseEntity<List<Programa>> getProgramas() {
        List<Programa> programas = programaService.getProgramas();
        return new ResponseEntity<>(programas, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un programa por su ID.
     * Retorna un programa específico basado en el ID proporcionado en la ruta.
     *
     * @param id El identificador único del programa a buscar.
     * @return Una respuesta {@link ResponseEntity} con el programa encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el programa.
     */
    @Operation(summary = "Obtener un programa por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Programa> getPrograma(@PathVariable Long id) {
        Optional<Programa> programa = programaService.getPrograma(id);

        if (programa.isPresent()) {
            return new ResponseEntity<>(programa.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para crear un nuevo programa.
     * Recibe los datos del nuevo programa en el cuerpo de la petición y lo guarda en la base de datos.
     *
     * @param programa El objeto {@link Programa} con los datos del nuevo programa.
     * @return Una respuesta {@link ResponseEntity} con el programa creado y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear un nuevo programa")
    @PostMapping
    public ResponseEntity<Programa> savePrograma(@RequestBody Programa programa) {
        Programa nuevoPrograma = programaService.savePrograma(programa);
        return new ResponseEntity<>(nuevoPrograma, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de un programa existente.
     * Recibe el ID del programa a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                El identificador único del programa a actualizar.
     * @param programaActualizado El objeto {@link Programa} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con el programa actualizado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el programa a actualizar.
     */
    @Operation(summary = "Actualizar un programa existente")
    @PutMapping("/{id}")
    public ResponseEntity<Programa> updatePrograma(@PathVariable Long id, @RequestBody Programa programaActualizado) {
        try {
            Programa programa = programaService.updatePrograma(id, programaActualizado);
            return new ResponseEntity<>(programa, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar un programa por su ID.
     * Recibe el ID del programa a eliminar en la ruta.
     *
     * @param id El identificador único del programa a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el programa a eliminar.
     */
    @Operation(summary = "Eliminar un programa por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrograma(@PathVariable Long id) {
        try {
            programaService.deletePrograma(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}