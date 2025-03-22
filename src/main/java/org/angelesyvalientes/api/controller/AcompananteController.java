package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.persistence.entity.Acompanante;
import org.angelesyvalientes.api.service.AcompananteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/acompanantes")
public class AcompananteController {

    private final AcompananteService acompananteService;

    @Autowired
    public AcompananteController(AcompananteService acompananteService) {
        this.acompananteService = acompananteService;
    }

    @GetMapping
    public ResponseEntity<List<Acompanante>> getAllAcompanantes() {
        List<Acompanante> acompanantes = acompananteService.getAllAcompanantes();
        return new ResponseEntity<>(acompanantes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acompanante> getAcompananteById(@PathVariable Long id) {
        Optional<Acompanante> acompanante = acompananteService.getAcompanante(id);
        return acompanante.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Acompanante> createAcompanante(@RequestBody Acompanante acompanante) {
        Acompanante nuevoAcompanante = acompananteService.createAcompanante(acompanante);
        return new ResponseEntity<>(nuevoAcompanante, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Acompanante> updateAcompanante(@PathVariable Long id, @RequestBody Acompanante acompananteActualizado) {
        try {
            Acompanante acompanante = acompananteService.updateAcompanante(id, acompananteActualizado);
            return new ResponseEntity<>(acompanante, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcompanante(@PathVariable Long id) {
        try {
            acompananteService.deleteAcompanante(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
