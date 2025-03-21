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

@Tag(name = "Programas")
@RestController
@RequestMapping("/api/programa")
public class ProgramaController {

    @Autowired
    private ProgramaService programaService;

    @Operation(summary = "Listar programas")
    @GetMapping
    public ResponseEntity<List<Programa>> getProgramas() {
        List<Programa> programas = programaService.getProgramas();
        return new ResponseEntity<>(programas, HttpStatus.OK);
    }

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

    @Operation(summary = "Crear un nuevo programa")
    @PostMapping
    public ResponseEntity<Programa> savePrograma(@RequestBody Programa programa) {
        Programa nuevoPrograma = programaService.savePrograma(programa);
        return new ResponseEntity<>(nuevoPrograma, HttpStatus.CREATED);
    }

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
