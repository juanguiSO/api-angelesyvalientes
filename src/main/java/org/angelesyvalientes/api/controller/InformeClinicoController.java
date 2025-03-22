package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.persistence.entity.InformeClinico;
import org.angelesyvalientes.api.service.InformeClinicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/informesclinicos")
public class InformeClinicoController {

    private final InformeClinicoService informeClinicoService;

    @Autowired
    public InformeClinicoController(InformeClinicoService informeClinicoService) {
        this.informeClinicoService = informeClinicoService;
    }

    @GetMapping
    public ResponseEntity<List<InformeClinico>> getAllInformesClinicos() {
        List<InformeClinico> informesClinicos = informeClinicoService.getAllInformesClinicos();
        return new ResponseEntity<>(informesClinicos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformeClinico> getInformeClinicoById(@PathVariable Long id) {
        Optional<InformeClinico> informeClinico = informeClinicoService.getInformeClinico(id);
        return informeClinico.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<InformeClinico> createInformeClinico(@RequestBody InformeClinico informeClinico) {
        InformeClinico nuevoInformeClinico = informeClinicoService.createInformeClinico(informeClinico);
        return new ResponseEntity<>(nuevoInformeClinico, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InformeClinico> updateInformeClinico(@PathVariable Long id, @RequestBody InformeClinico informeClinicoActualizado) {
        try {
            InformeClinico informeClinico = informeClinicoService.updateInformeClinico(id, informeClinicoActualizado);
            return new ResponseEntity<>(informeClinico, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInformeClinico(@PathVariable Long id) {
        try {
            informeClinicoService.deleteInformeClinico(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}