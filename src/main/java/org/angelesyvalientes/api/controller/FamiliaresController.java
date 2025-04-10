package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Familiar;
import org.angelesyvalientes.api.service.FamiliaresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Familiar}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar familiares.
 * La API está etiquetada como "Familiares" en la documentación de Swagger.
 */
@RestController
@RequestMapping("/familiares")
@Tag(name = "Familiares")
public class FamiliaresController {

    private final FamiliaresService familiaresService;

    /**
     * Constructor de la clase {@code FamiliaresController}.
     * Recibe una instancia de {@link FamiliaresService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con los familiares.
     *
     * @param familiaresService El servicio para la gestión de familiares.
     */
    @Autowired
    public FamiliaresController(FamiliaresService familiaresService) {
        this.familiaresService = familiaresService;
    }

    /**
     * Endpoint para listar todos los familiares.
     * Retorna una lista de todos los familiares almacenados en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de familiares y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todas los familiares ")
    @GetMapping
    public ResponseEntity<List<Familiar>> obtenerTodosLosFamiliares() {
        List<Familiar> familiares = familiaresService.obtenerTodosLosFamiliares();
        return new ResponseEntity<>(familiares, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un familiar por su ID.
     * Retorna un familiar específico basado en el ID proporcionado en la ruta.
     *
     * @param id El identificador único del familiar a buscar.
     * @return Una respuesta {@link ResponseEntity} con el familiar encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el familiar.
     */
    @Operation(summary = "Obtener un familiar  por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Familiar> obtenerFamiliarPorId(@PathVariable int id) {
        Optional<Familiar> familiar = familiaresService.obtenerFamiliarPorId(id);
        return familiar.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para crear un nuevo familiar.
     * Recibe los datos del nuevo familiar en el cuerpo de la petición y lo guarda en la base de datos.
     *
     * @param familiares El objeto {@link Familiar} con los datos del nuevo familiar.
     * @return Una respuesta {@link ResponseEntity} con el familiar creado y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear un familiar  ")
    @PostMapping
    public ResponseEntity<Familiar> crearFamiliar(@RequestBody Familiar familiares) {
        Familiar nuevoFamiliar = familiaresService.crearFamiliar(familiares);
        return new ResponseEntity<>(nuevoFamiliar, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de un familiar existente.
     * Recibe el ID del familiar a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                  El identificador único del familiar a actualizar.
     * @param familiarActualizado El objeto {@link Familiar} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con el familiar actualizado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el familiar a actualizar.
     */
    @Operation(summary = "Actualizar un familiar por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Familiar> actualizarFamiliar(@PathVariable int id, @RequestBody Familiar familiarActualizado) {
        Familiar familiarActualizadoResult = familiaresService.actualizarFamiliar(id, familiarActualizado);
        if (familiarActualizadoResult != null) {
            return new ResponseEntity<>(familiarActualizadoResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar un familiar por su ID.
     * Recibe el ID del familiar a eliminar en la ruta.
     *
     * @param id El identificador único del familiar a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa.
     */
    @Operation(summary = "Eliminar un familiar por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFamiliar(@PathVariable int id) {
        familiaresService.eliminarFamiliar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}