package org.angelesyvalientes.api.controller;

import jakarta.validation.Valid;
import org.angelesyvalientes.api.persistence.entity.GrupoEtnico;
import org.angelesyvalientes.api.service.GrupoEtnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/grupos-etnicos")
public class GrupoEtnicoController {

    private final GrupoEtnicoService grupoEtnicoService;

    @Autowired
    public GrupoEtnicoController(GrupoEtnicoService grupoEtnicoService) {
        this.grupoEtnicoService = grupoEtnicoService;
    }

    @GetMapping
    public ResponseEntity<List<GrupoEtnico>> getAllGruposEtnicos() {
        List<GrupoEtnico> gruposEtnicos = grupoEtnicoService.getAllGruposEtnicos();
        return new ResponseEntity<>(gruposEtnicos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoEtnico> getGrupoEtnicoById(@PathVariable Integer id) {
        Optional<GrupoEtnico> grupoEtnico = grupoEtnicoService.getGrupoEtnicoById(id);
        return grupoEtnico.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<GrupoEtnico> createGrupoEtnico(@Valid @RequestBody GrupoEtnico grupoEtnico) {
        GrupoEtnico savedGrupoEtnico = grupoEtnicoService.saveGrupoEtnico(grupoEtnico);
        return new ResponseEntity<>(savedGrupoEtnico, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoEtnico> updateGrupoEtnico(@PathVariable Integer id, @Valid @RequestBody GrupoEtnico grupoEtnico) {
        Optional<GrupoEtnico> existingGrupoEtnico = grupoEtnicoService.getGrupoEtnicoById(id);
        if (existingGrupoEtnico.isPresent()) {
            grupoEtnico.setId((int) id); // Asegurar que el ID sea el correcto para la actualización
            GrupoEtnico updatedGrupoEtnico = grupoEtnicoService.saveGrupoEtnico(grupoEtnico);
            return new ResponseEntity<>(updatedGrupoEtnico, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupoEtnico(@PathVariable Integer id) {
        if (grupoEtnicoService.getGrupoEtnicoById(id).isPresent()) {
            grupoEtnicoService.deleteGrupoEtnico(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}