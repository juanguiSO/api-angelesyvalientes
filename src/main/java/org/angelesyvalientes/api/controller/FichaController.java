package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Ficha;
import org.angelesyvalientes.api.service.FichaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Tag(name = "Fichas", description = "Controlador para gestionar las Fichas")
@RestController
@RequestMapping("/api/fichas")
public class FichaController {
    private static final Logger logger = LoggerFactory.getLogger(FichaController.class);

    private final FichaService fichaService;

    @Autowired
    public FichaController(FichaService fichaService) {
        this.fichaService = fichaService;
    }

    @Operation(summary = "Listar todas las fichas")
    @GetMapping
    public List<Ficha> listarFichas() {
        return fichaService.obtenerTodas();
    }

    @Operation(summary = "Obtener una ficha por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Ficha> obtenerFicha(@PathVariable int id) {
        return fichaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createFicha(@RequestPart("ficha") Ficha ficha,
                                         @RequestPart(value = "archivoRecurso", required = false) MultipartFile archivoRecurso) {
        if (archivoRecurso == null || archivoRecurso.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo PDF del recurso es obligatorio.");
        }

        try {
            Ficha createdFicha = fichaService.guardar(ficha, archivoRecurso);
            return new ResponseEntity<>(createdFicha, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación al crear/actualizar ficha: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage()); // Devuelve 400 Bad Request
        } catch (RuntimeException e) {
            logger.error("Error inesperado al crear/actualizar ficha: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor al procesar la ficha.");
        }
    }
    /**
     @Operation(summary = "Actualizar una ficha existente y su recurso PDF (obligatorio para la actualización)")
     @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
     public ResponseEntity<Ficha> actualizarFicha(
     @PathVariable int id,
     @RequestPart("ficha") Ficha ficha,
     @RequestPart(value = "archivoRecurso", required = true) MultipartFile archivoRecurso) { // <-- CAMBIO AQUÍ: required = true
     return fichaService.obtenerPorId(id)
     .map(fichaExistente -> {
     ficha.setId(id); // Asegura que el ID de la ficha en el cuerpo sea el del path
     try {
     if (archivoRecurso == null || archivoRecurso.isEmpty()) { // Validar explícitamente si el archivo está vacío
     return ResponseEntity.badRequest().body(null); // O un mensaje más específico
     }
     Ficha actualizada = fichaService.guardar(ficha, archivoRecurso);
     return ResponseEntity.ok(actualizada);
     } catch (IllegalArgumentException e) {
     return ResponseEntity.badRequest().body(null);
     } catch (RuntimeException e) {
     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
     }
     })
     .orElse(ResponseEntity.notFound().build());
     }*/

    @Operation(summary = "Eliminar una ficha por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFicha(@PathVariable int id) {
        try {
            fichaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}