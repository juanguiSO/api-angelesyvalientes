package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.TipoIdentificacion;
import org.angelesyvalientes.api.service.TipoIdentificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link TipoIdentificacion}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar tipos de identificación.
 * La API está etiquetada como "Tipos de Identificacion" en la documentación de Swagger.
 */
@Tag(name = "Tipos de Identificacion")
@RestController
@RequestMapping("/api/tipos-identificacion")
public class TipoIdentificacionController {

    private final TipoIdentificacionService tipoIdentificacionService;

    /**
     * Constructor de la clase {@code TipoIdentificacionController}.
     * Recibe una instancia de {@link TipoIdentificacionService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con los tipos de identificación.
     *
     * @param tipoIdentificacionService El servicio para la gestión de tipos de identificación.
     */
    @Autowired
    public TipoIdentificacionController(TipoIdentificacionService tipoIdentificacionService) {
        this.tipoIdentificacionService = tipoIdentificacionService;
    }

    /**
     * Endpoint para listar todos los tipos de identificación.
     * Retorna una lista de todos los tipos de identificación almacenados en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de tipos de identificación y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar tipos de identificación")
    @GetMapping
    public ResponseEntity<List<TipoIdentificacion>> getTiposIdentificacion() {
        List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionService.getTiposIdentificacion();
        return new ResponseEntity<>(tiposIdentificacion, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener un tipo de identificación por su ID.
     * Retorna un tipo de identificación específico basado en el ID proporcionado en la ruta.
     *
     * @param id El identificador único del tipo de identificación a buscar.
     * @return Una respuesta {@link ResponseEntity} con el tipo de identificación encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el tipo de identificación.
     */
    @Operation(summary = "Obtener un tipo de identificación por su Id")
    @GetMapping("/{id}")
    public ResponseEntity<TipoIdentificacion> getTipoIdentificacion(@PathVariable int id) {
        Optional<TipoIdentificacion> tipoIdentificacion = tipoIdentificacionService.getTipoIdentificacion(id);

        if (tipoIdentificacion.isPresent()) {
            return new ResponseEntity<>(tipoIdentificacion.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para crear un nuevo tipo de identificación.
     * Recibe los datos del nuevo tipo de identificación en el cuerpo de la petición y lo guarda en la base de datos.
     *
     * @param tipoIdentificacion El objeto {@link TipoIdentificacion} con los datos del nuevo tipo de identificación.
     * @return Una respuesta {@link ResponseEntity} con el tipo de identificación creado y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear tipo de identificación")
    @PostMapping
    public ResponseEntity<TipoIdentificacion> saveTipoIdentificacion(@RequestBody TipoIdentificacion tipoIdentificacion) {
        TipoIdentificacion nuevoTipoIdentificacion = tipoIdentificacionService.saveTipoIdentificacion(tipoIdentificacion);
        return new ResponseEntity<>(nuevoTipoIdentificacion, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de un tipo de identificación existente.
     * Recibe el ID del tipo de identificación a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                         El identificador único del tipo de identificación a actualizar.
     * @param tipoIdentificacionActualizado El objeto {@link TipoIdentificacion} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con el tipo de identificación actualizado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el tipo de identificación a actualizar.
     */
    @Operation(summary = "Actualizar tipo de identificación por Id")
    @PutMapping("/{id}")
    public ResponseEntity<TipoIdentificacion> updateTipoIdentificacion(@PathVariable int id, @RequestBody TipoIdentificacion tipoIdentificacionActualizado) {
        try {
            TipoIdentificacion tipoIdentificacion = tipoIdentificacionService.updateTipoIdentificacion(id, tipoIdentificacionActualizado);
            return new ResponseEntity<>(tipoIdentificacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar un tipo de identificación por su ID.
     * Recibe el ID del tipo de identificación a eliminar en la ruta.
     *
     * @param id El identificador único del tipo de identificación a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el tipo de identificación a eliminar.
     */
    @Operation(summary = "Eliminar un tipo de identificación por Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoIdentificacion(@PathVariable int id) {
        try {
            tipoIdentificacionService.deleteTipoIdentificacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
