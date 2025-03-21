package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Genero;
import org.angelesyvalientes.api.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Géneros")
@RestController
@RequestMapping("/api/generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @Operation(summary = "Listar géneros")
    @GetMapping
    public ResponseEntity<List<Genero>> getGeneros() {
        List<Genero> generos = generoService.getGeneros();
        return new ResponseEntity<>(generos, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un género por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Genero> getGenero(@PathVariable Long id) {
        Optional<Genero> genero = generoService.getGenero(id);

        if (genero.isPresent()) {
            return new ResponseEntity<>(genero.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear un nuevo género")
    @PostMapping
    public ResponseEntity<Genero> saveGenero(@RequestBody Genero genero) {
        Genero nuevoGenero = generoService.saveGenero(genero);
        return new ResponseEntity<>(nuevoGenero, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un género existente")
    @PutMapping("/{id}")
    public ResponseEntity<Genero> updateGenero(@PathVariable Long id, @RequestBody Genero generoActualizado) {
        try {
            Genero genero = generoService.updateGenero(id, generoActualizado);
            return new ResponseEntity<>(genero, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar un género por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenero(@PathVariable Long id) {
        try {
            generoService.deleteGenero(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
