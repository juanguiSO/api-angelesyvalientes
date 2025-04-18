package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.angelesyvalientes.api.persistence.entity.ClasificacionValiente;
import org.angelesyvalientes.api.service.ClasificacionValienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Clasificaciones de Valientes")
@RestController
@RequestMapping("/api/clasificaciones-valientes")
public class ClasificacionValienteController {

    private final ClasificacionValienteService clasificacionValienteService;

    @Autowired
    public ClasificacionValienteController(ClasificacionValienteService clasificacionValienteService) {
        this.clasificacionValienteService = clasificacionValienteService;
    }

    @Operation(summary = "Obtener todas las clasificaciones de valientes")
    @GetMapping
    public ResponseEntity<List<ClasificacionValiente>> obtenerTodasClasificaciones() {
        List<ClasificacionValiente> clasificaciones = clasificacionValienteService.obtenerTodasClasificaciones();
        return new ResponseEntity<>(clasificaciones, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una clasificación de valiente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClasificacionValiente> obtenerClasificacionPorId(@PathVariable Long id) {
        Optional<ClasificacionValiente> clasificacion = clasificacionValienteService.obtenerClasificacionPorId(id);
        return clasificacion.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Crear una nueva clasificación de valiente")
    @PostMapping
    public ResponseEntity<ClasificacionValiente> guardarClasificacion(@Valid @RequestBody ClasificacionValiente clasificacionValiente) {
        ClasificacionValiente nuevaClasificacion = clasificacionValienteService.guardarClasificacion(clasificacionValiente);
        return new ResponseEntity<>(nuevaClasificacion, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una clasificación de valiente existente")
    @PutMapping("/{id}")
    public ResponseEntity<ClasificacionValiente> actualizarClasificacion(
            @PathVariable Long id,
            @Valid @RequestBody ClasificacionValiente clasificacionValiente) {
        Optional<ClasificacionValiente> clasificacionActualizada = clasificacionValienteService.actualizarClasificacion(id, clasificacionValiente);
        return clasificacionActualizada.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Eliminar una clasificación de valiente por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarClasificacion(@PathVariable Long id) {
        if (clasificacionValienteService.eliminarClasificacion(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}