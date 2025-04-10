package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Donacion;
import org.angelesyvalientes.api.service.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Donacion}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar donaciones.
 * La API está etiquetada como "Donaciones" en la documentación de Swagger.
 */
@Tag(name = "Donaciones")
@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {

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
    public ResponseEntity<List<Donacion>> getAllDonaciones() {
        List<Donacion> donaciones = donacionService.getAllDonaciones();
        return new ResponseEntity<>(donaciones, HttpStatus.OK);
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
    public ResponseEntity<Donacion> getDonacionById(@PathVariable Long id) {
        Optional<Donacion> donacion = donacionService.getDonacion(id);
        return donacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para crear una nueva donación.
     * Recibe los datos de la nueva donación en el cuerpo de la petición y la guarda en la base de datos.
     *
     * @param donacion El objeto {@link Donacion} con los datos de la nueva donación.
     * @return Una respuesta {@link ResponseEntity} con la donación creada y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear donación")
    @PostMapping
    public ResponseEntity<Donacion> createDonacion(@RequestBody Donacion donacion) {
        Donacion nuevaDonacion = donacionService.createDonacion(donacion);
        return new ResponseEntity<>(nuevaDonacion, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de una donación existente.
     * Recibe el ID de la donación a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                  El identificador único de la donación a actualizar.
     * @param donacionActualizada El objeto {@link Donacion} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con la donación actualizada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la donación a actualizar.
     */
    @Operation(summary = "Actualizar una donación por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Donacion> updateDonacion(@PathVariable Long id, @RequestBody Donacion donacionActualizada) {
        try {
            Donacion donacion = donacionService.updateDonacion(id, donacionActualizada);
            return new ResponseEntity<>(donacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Void> deleteDonacion(@PathVariable Long id) {
        try {
            donacionService.deleteDonacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}