package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.persistence.entity.Documentacion;
import org.angelesyvalientes.api.service.DocumentacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documentaciones")
public class DocumentacionController {

    private final DocumentacionService documentacionService;

    @Autowired
    public DocumentacionController(DocumentacionService documentacionService) {
        this.documentacionService = documentacionService;
    }

    @GetMapping
    public ResponseEntity<List<Documentacion>> getAllDocumentaciones() {
        List<Documentacion> documentaciones = documentacionService.getAllDocumentaciones();
        return new ResponseEntity<>(documentaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documentacion> getDocumentacionById(@PathVariable Long id) {
        Optional<Documentacion> documentacion = documentacionService.getDocumentacion(id);
        return documentacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Documentacion> createDocumentacion(@RequestBody Documentacion documentacion) {
        Documentacion nuevaDocumentacion = documentacionService.createDocumentacion(documentacion);
        return new ResponseEntity<>(nuevaDocumentacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documentacion> updateDocumentacion(@PathVariable Long id, @RequestBody Documentacion documentacionActualizada) {
        try {
            Documentacion documentacion = documentacionService.updateDocumentacion(id, documentacionActualizada);
            return new ResponseEntity<>(documentacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentacion(@PathVariable Long id) {
        try {
            documentacionService.deleteDocumentacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}