package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.persistence.entity.Angel;
import org.angelesyvalientes.api.service.AngelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/angeles")
public class AngelController {

    private final AngelService angelService;

    @Autowired
    public AngelController(AngelService angelService) {
        this.angelService = angelService;
    }

    @GetMapping
    public ResponseEntity<List<Angel>> getAllAngeles() {
        List<Angel> angeles = angelService.getAllAngeles();
        return new ResponseEntity<>(angeles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Angel> getAngelById(@PathVariable Long id) {
        Optional<Angel> angel = angelService.getAngelById(id);
        return angel.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Angel> createAngel(@RequestBody Angel angel) {
        Angel createdAngel = angelService.saveAngel(angel);
        return new ResponseEntity<>(createdAngel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Angel> updateAngel(@PathVariable Long id, @RequestBody Angel angelDetails) {
        Angel updatedAngel = angelService.updateAngel(id, angelDetails);
        if (updatedAngel != null) {
            return new ResponseEntity<>(updatedAngel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAngel(@PathVariable Long id) {
        angelService.deleteAngel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}