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

@Tag(name = "Tipos de Identificacion")
@RestController
@RequestMapping("/api/tipos-identificacion")
public class TipoIdentificacionController {

    @Autowired
    private TipoIdentificacionService tipoIdentificacionService;

    @Operation(summary = "Listar tipos de identificación")
    @GetMapping
    public ResponseEntity<List<TipoIdentificacion>> getTiposIdentificacion() {
        List<TipoIdentificacion> tiposIdentificacion = tipoIdentificacionService.getTiposIdentificacion();
        return new ResponseEntity<>(tiposIdentificacion, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un tipo de identificación por su Id")
    @GetMapping("/{id}")
    public ResponseEntity<TipoIdentificacion> getTipoIdentificacion(@PathVariable Long id) {
        Optional<TipoIdentificacion> tipoIdentificacion = tipoIdentificacionService.getTipoIdentificacion(id);

        if (tipoIdentificacion.isPresent()) {
            return new ResponseEntity<>(tipoIdentificacion.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear tipo de identificación")
    @PostMapping
    public ResponseEntity<TipoIdentificacion> saveTipoIdentificacion(@RequestBody TipoIdentificacion tipoIdentificacion) {
        TipoIdentificacion nuevoTipoIdentificacion = tipoIdentificacionService.saveTipoIdentificacion(tipoIdentificacion);
        return new ResponseEntity<>(nuevoTipoIdentificacion, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar tipo de identificación por Id")
    @PutMapping("/{id}")
    public ResponseEntity<TipoIdentificacion> updateTipoIdentificacion(@PathVariable Long id, @RequestBody TipoIdentificacion tipoIdentificacionActualizado) {
        try {
            TipoIdentificacion tipoIdentificacion = tipoIdentificacionService.updateTipoIdentificacion(id, tipoIdentificacionActualizado);
            return new ResponseEntity<>(tipoIdentificacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un TipoIdentificacion por su ID
    @Operation(summary = "Eliminar un tipo de identificación por Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoIdentificacion(@PathVariable Long id) {
        try {
            tipoIdentificacionService.deleteTipoIdentificacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
