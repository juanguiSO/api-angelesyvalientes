package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Valiente;
import org.angelesyvalientes.api.service.ValienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Valientes")
@RestController
@RequestMapping("/api/valientes")
public class ValienteController {

    @Autowired
    private ValienteService valienteService;

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

    @Operation(summary = "Crear valiente")
    @PostMapping
    public ResponseEntity<Valiente> saveValiente(@RequestBody Valiente valiente) {
        Valiente nuevaValiente = valienteService.saveValiente(valiente);
        return new ResponseEntity<>(nuevaValiente, HttpStatus.CREATED);
    }

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
