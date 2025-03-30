package org.angelesyvalientes.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper; // Importa ObjectMapper
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Valiente;
//import org.angelesyvalientes.api.service.GoogleDriveService; // Importa GoogleDriveService
import org.angelesyvalientes.api.service.ValienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // Importa MultipartFile

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name = "Valientes")
@RestController
@RequestMapping("/api/valientes")
public class ValienteController {

    @Autowired
    private ValienteService valienteService;

   // @Autowired
  //  private GoogleDriveService googleDriveService; // Inyecta GoogleDriveService

    @Autowired
    private ObjectMapper objectMapper; // Inyecta ObjectMapper

    @Operation(summary = "Listar todos los valientes")
    @GetMapping
    public ResponseEntity<List<Valiente>> getValientes() {
        List<Valiente> valiente = valienteService.getValientes();
        return new ResponseEntity<>(valiente, HttpStatus.OK);
    }

    @Operation(summary = "Obtener valiente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Valiente> getValiente(@PathVariable Long id) {
        Optional<Valiente> valiente = valienteService.getValiente(id);

        if (valiente.isPresent()) {
            return new ResponseEntity<>(valiente.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
/**
    @Operation(summary = "Crear valiente con foto")
    @PostMapping(consumes = {"multipart/form-data"}) // Indica que acepta multipart/form-data
    public ResponseEntity<Valiente> saveValiente(
            @RequestParam("file") MultipartFile file,
            @RequestParam("valiente") String valienteJson) {

        try {
            Valiente valiente = objectMapper.readValue(valienteJson, Valiente.class);
            String driveUrl = googleDriveService.uploadFile(file);
            valiente.setUrlGaleria(driveUrl);
            Valiente nuevaValiente = valienteService.saveValiente(valiente);
            return new ResponseEntity<>(nuevaValiente, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @Operation(summary = "Actualizar valiente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Valiente> updateValiente(@PathVariable Long id, @RequestBody Valiente valienteActualizada) {
        try {
            Valiente valiente = valienteService.updateValiente(id, valienteActualizada);
            return new ResponseEntity<>(valiente, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar valiente por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValiente(@PathVariable Long id) {
        try {
            valienteService.deleteValiente(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Asignar ficha al Valiente")
    @GetMapping("/{id}/asignarFicha/{idFicha}")
    public ResponseEntity<Valiente> asignarFicha(@PathVariable int id, @PathVariable int idFicha) {
        valienteService.asignarFicha(id, idFicha);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}