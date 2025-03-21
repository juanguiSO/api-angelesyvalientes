package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.TipoDonacion;
import org.angelesyvalientes.api.service.TipoDonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "TipoDonacion")
@RestController
@RequestMapping("/api/tipodonacion")
public class TipoDonacionController {

    @Autowired
    private TipoDonacionService tipoDonacionService;

    @GetMapping
    public ResponseEntity<List<TipoDonacion>> getTipoDonaciones() {
        List<TipoDonacion> tipoDonaciones = tipoDonacionService.getTipoDonaciones();
        return new ResponseEntity<>(tipoDonaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDonacion> getTipoDonacion(@PathVariable Long id) {
        Optional<TipoDonacion> tipoDonacion = tipoDonacionService.getTipoDonacion(id);
        return tipoDonacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<TipoDonacion> createTipoDonacion(@RequestBody TipoDonacion tipoDonacion) {
        TipoDonacion createdTipoDonacion = tipoDonacionService.saveTipoDonacion(tipoDonacion);
        return new ResponseEntity<>(createdTipoDonacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDonacion> updateTipoDonacion(@PathVariable Long id, @RequestBody TipoDonacion tipoDonacion) {
        Optional<TipoDonacion> existingTipoDonacion = tipoDonacionService.getTipoDonacion(id);
        if (existingTipoDonacion.isPresent()) {
            TipoDonacion updatedTipoDonacion = tipoDonacionService.saveTipoDonacion(tipoDonacion);
            return new ResponseEntity<>(updatedTipoDonacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoDonacion(@PathVariable Long id) {
        tipoDonacionService.deleteTipoDonacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}