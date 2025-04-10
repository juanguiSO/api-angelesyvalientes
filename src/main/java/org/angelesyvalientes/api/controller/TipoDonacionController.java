package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.TipoDonacion;
import org.angelesyvalientes.api.service.TipoDonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link TipoDonacion}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar tipos de donación.
 * La API está etiquetada como "Tipo de donaciones" en la documentación de Swagger.
 */
@Tag(name = "Tipo de donaciones")
@RestController
@RequestMapping("/api/tipos-donacion")
public class TipoDonacionController {

    private final TipoDonacionService tipoDonacionService;

    /**
     * Constructor de la clase {@code TipoDonacionController}.
     * Recibe una instancia de {@link TipoDonacionService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con los tipos de donación.
     *
     * @param tipoDonacionService El servicio para la gestión de tipos de donación.
     */
    @Autowired
    public TipoDonacionController(TipoDonacionService tipoDonacionService) {
        this.tipoDonacionService = tipoDonacionService;
    }

    /**
     * Endpoint para listar todos los tipos de donaciones.
     * Retorna una lista de todos los tipos de donaciones almacenados en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de tipos de donaciones y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todos los Tipos de donaciones")
    @GetMapping
    public ResponseEntity<List<TipoDonacion>> getAllTipoDonaciones() {
        List<TipoDonacion> tiposDonacion = tipoDonacionService.getAllTipoDonaciones();
        return new ResponseEntity<>(tiposDonacion, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un tipo de donación por su ID.
     * Retorna un tipo de donación específico basado en el ID proporcionado en la ruta.
     *
     * @param id El identificador único del tipo de donación a buscar.
     * @return Una respuesta {@link ResponseEntity} con el tipo de donación encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el tipo de donación.
     */
    @Operation(summary = "Obtener una  por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<TipoDonacion> getTipoDonacionById(@PathVariable Long id) {
        Optional<TipoDonacion> tipoDonacion = tipoDonacionService.getTipoDonacion(id);
        return tipoDonacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para crear un nuevo tipo de donación.
     * Recibe los datos del nuevo tipo de donación en el cuerpo de la petición y lo guarda en la base de datos.
     *
     * @param tipoDonacion El objeto {@link TipoDonacion} con los datos del nuevo tipo de donación.
     * @return Una respuesta {@link ResponseEntity} con el tipo de donación creado y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear  ")
    @PostMapping
    public ResponseEntity<TipoDonacion> createTipoDonacion(@RequestBody TipoDonacion tipoDonacion) {
        TipoDonacion nuevoTipoDonacion = tipoDonacionService.createTipoDonacion(tipoDonacion);
        return new ResponseEntity<>(nuevoTipoDonacion, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de un tipo de donación existente.
     * Recibe el ID del tipo de donación a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                    El identificador único del tipo de donación a actualizar.
     * @param tipoDonacionActualizado El objeto {@link TipoDonacion} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con el tipo de donación actualizado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el tipo de donación a actualizar.
     */
    @Operation(summary = "Actualizar una   por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<TipoDonacion> updateTipoDonacion(@PathVariable Long id, @RequestBody TipoDonacion tipoDonacionActualizado) {
        try {
            TipoDonacion tipoDonacion = tipoDonacionService.updateTipoDonacion(id, tipoDonacionActualizado);
            return new ResponseEntity<>(tipoDonacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar un tipo de donación por su ID.
     * Recibe el ID del tipo de donación a eliminar en la ruta.
     *
     * @param id El identificador único del tipo de donación a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el tipo de donación a eliminar.
     */
    @Operation(summary = "Eliminar una   por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoDonacion(@PathVariable Long id) {
        try {
            tipoDonacionService.deleteTipoDonacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}