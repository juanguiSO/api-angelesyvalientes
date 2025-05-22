package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Vivienda;
import org.angelesyvalientes.api.service.ViviendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Vivienda}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar viviendas.
 * La API está etiquetada como "Viviendas" en la documentación de Swagger.
 */
@Tag(name = "Viviendas")
@RestController
@RequestMapping("/api/viviendas")
public class ViviendaController {

    private final ViviendaService viviendaService;

    /**
     * Constructor de la clase {@code ViviendaController}.
     * Recibe una instancia de {@link ViviendaService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con las viviendas.
     *
     * @param viviendaService El servicio para la gestión de viviendas.
     */
    @Autowired
    public ViviendaController(ViviendaService viviendaService) {
        this.viviendaService = viviendaService;
    }

    /**
     * Endpoint para listar todas las viviendas.
     * Retorna una lista de todas las viviendas almacenadas en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de viviendas y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todas las viviendas")
    @GetMapping
    public ResponseEntity<List<Vivienda>> obtenerTodasLasViviendas() {
        List<Vivienda> viviendas = viviendaService.obtenerTodasLasViviendas();
        return new ResponseEntity<>(viviendas, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener una vivienda por su ID.
     * Retorna una vivienda específica basada en el ID proporcionado en la ruta.
     *
     * @param id El identificador único de la vivienda a buscar.
     * @return Una respuesta {@link ResponseEntity} con la vivienda encontrada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la vivienda.
     */
    @Operation(summary = "Obtener una vivienda por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Vivienda> obtenerViviendaPorId(@PathVariable int id) {
        Optional<Vivienda> vivienda = viviendaService.obtenerViviendaPorId(id);
        return vivienda.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para crear una nueva vivienda.
     * Recibe los datos de la nueva vivienda en el cuerpo de la petición y la guarda en la base de datos.
     *
     * @param vivienda El objeto {@link Vivienda} con los datos de la nueva vivienda.
     * @return Una respuesta {@link ResponseEntity} con la vivienda creada y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear una vivienda")
    @PostMapping
    public ResponseEntity<Vivienda> crearVivienda(@RequestBody Vivienda vivienda) {
        Vivienda nuevaVivienda = viviendaService.crearVivienda(vivienda);
        return new ResponseEntity<>(nuevaVivienda, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de una vivienda existente.
     * Recibe el ID de la vivienda a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                 El identificador único de la vivienda a actualizar.
     * @param viviendaActualizada El objeto {@link Vivienda} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con la vivienda actualizada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la vivienda a actualizar.
     */
    @Operation(summary = "Actualizar una vivienda por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Vivienda> actualizarVivienda(@PathVariable int id, @RequestBody Vivienda viviendaActualizada) {
        Vivienda viviendaActualizadaResult = viviendaService.actualizarVivienda(id, viviendaActualizada);
        if (viviendaActualizadaResult != null) {
            return new ResponseEntity<>(viviendaActualizadaResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar una vivienda por su ID.
     * Recibe el ID de la vivienda a eliminar en la ruta.
     *
     * @param id El identificador único de la vivienda a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa.
     */
    @Operation(summary = "Eliminar una vivienda por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVivienda(@PathVariable int id) {
        viviendaService.eliminarVivienda(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getViviendasCount() {
        long count = viviendaService.countAllViviendas();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}