package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.persistence.entity.TipoDonacion;
import org.angelesyvalientes.api.service.TipoDonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-donacion")
public class TipoDonacionController {

    private final TipoDonacionService tipoDonacionService;

    @Autowired
    public TipoDonacionController(TipoDonacionService tipoDonacionService) {
        this.tipoDonacionService = tipoDonacionService;
    }

    @GetMapping
    public ResponseEntity<List<TipoDonacion>> getAllTipoDonaciones() {
        List<TipoDonacion> tiposDonacion = tipoDonacionService.getAllTipoDonaciones();
        return new ResponseEntity<>(tiposDonacion, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDonacion> getTipoDonacionById(@PathVariable Long id) {
        Optional<TipoDonacion> tipoDonacion = tipoDonacionService.getTipoDonacion(id);
        return tipoDonacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<TipoDonacion> createTipoDonacion(@RequestBody TipoDonacion tipoDonacion) {
        TipoDonacion nuevoTipoDonacion = tipoDonacionService.createTipoDonacion(tipoDonacion);
        return new ResponseEntity<>(nuevoTipoDonacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDonacion> updateTipoDonacion(@PathVariable Long id, @RequestBody TipoDonacion tipoDonacionActualizado) {
        try {
            TipoDonacion tipoDonacion = tipoDonacionService.updateTipoDonacion(id, tipoDonacionActualizado);
            return new ResponseEntity<>(tipoDonacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoDonacion(@PathVariable Long id) {
        try {
            tipoDonacionService.deleteTipoDonacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}