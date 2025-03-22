package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.persistence.entity.Donacion;
import org.angelesyvalientes.api.service.DonacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/donaciones")
public class DonacionController {

    private final DonacionService donacionService;

    @Autowired
    public DonacionController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    @GetMapping
    public ResponseEntity<List<Donacion>> getAllDonaciones() {
        List<Donacion> donaciones = donacionService.getAllDonaciones();
        return new ResponseEntity<>(donaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donacion> getDonacionById(@PathVariable Long id) {
        Optional<Donacion> donacion = donacionService.getDonacion(id);
        return donacion.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Donacion> createDonacion(@RequestBody Donacion donacion) {
        Donacion nuevaDonacion = donacionService.createDonacion(donacion);
        return new ResponseEntity<>(nuevaDonacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donacion> updateDonacion(@PathVariable Long id, @RequestBody Donacion donacionActualizada) {
        try {
            Donacion donacion = donacionService.updateDonacion(id, donacionActualizada);
            return new ResponseEntity<>(donacion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonacion(@PathVariable Long id) {
        try {
            donacionService.deleteDonacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}