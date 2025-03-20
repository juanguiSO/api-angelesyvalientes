package org.angelesyvalientes.api.controller;

import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    // Obtener todas las Personas
    @GetMapping
    public ResponseEntity<List<Persona>> getPersonas() {
        List<Persona> personas = personaService.getPersonas();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    // Obtener una Persona por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersona(@PathVariable Long id) {
        Optional<Persona> persona = personaService.getPersona(id);

        if (persona.isPresent()) {
            return new ResponseEntity<>(persona.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear una nueva Persona
    @PostMapping
    public ResponseEntity<Persona> savePersona(@RequestBody Persona persona) {
        Persona nuevaPersona = personaService.savePersona(persona);
        return new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED);
    }

    // Actualizar una Persona existente
    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        try {
            Persona persona = personaService.updatePersona(id, personaActualizada);
            return new ResponseEntity<>(persona, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una Persona por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        try {
            personaService.deletePersona(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
