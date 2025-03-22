package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.persistence.entity.Educacion;
import org.angelesyvalientes.api.service.EducacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/educaciones")
public class EducacionController {

    private final EducacionService educacionService;

    @Autowired
    public EducacionController(EducacionService educacionService) {
        this.educacionService = educacionService;
    }

    @GetMapping
    public ResponseEntity<List<Educacion>> getAllEducaciones() {
        List<Educacion> educaciones = educacionService.getAllEducaciones();
        return new ResponseEntity<>(educaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Educacion> getEducacionById(@PathVariable Long id) {
        Optional<Educacion> educacion = educacionService.getEducacion(id);
        return educacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Educacion> createEducacion(@RequestBody Educacion educacion) {
        Educacion nuevaEducacion = educacionService.createEducacion(educacion);
        return new ResponseEntity<>(nuevaEducacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Educacion> updateEducacion(@PathVariable Long id, @RequestBody Educacion educacionActualizada) {
        try {
            Educacion educacion = educacionService.updateEducacion(id, educacionActualizada);
            return new ResponseEntity<>(educacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducacion(@PathVariable Long id) {
        try {
            educacionService.deleteEducacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}