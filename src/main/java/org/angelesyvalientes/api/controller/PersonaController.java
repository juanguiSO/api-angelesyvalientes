package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Persona}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar personas.
 * La API está etiquetada como "Personas" en la documentación de Swagger.
 */
@Tag(name = "Personas")
@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaService personaService;

    /**
     * Constructor de la clase {@code PersonaController}.
     * Recibe una instancia de {@link PersonaService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con las personas.
     *
     * @param personaService El servicio para la gestión de personas.
     */
    @Autowired
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    /**
     * Endpoint para listar todas las personas.
     * Retorna una lista de todas las personas almacenadas en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de personas y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todas las personas")
    @GetMapping
    public ResponseEntity<List<Persona>> getAllPersonas() {
        List<Persona> personas = personaService.getAllPersonas();
        return new ResponseEntity<>(personas, HttpStatus.OK);
    }

    /**
     * Endpoint para obtener una persona por su ID.
     * Retorna una persona específica basada en el ID proporcionado en la ruta.
     *
     * @param id El identificador único de la persona a buscar.
     * @return Una respuesta {@link ResponseEntity} con la persona encontrada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la persona.
     */
    @Operation(summary = "Obtener una persona por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long id) {
        Optional<Persona> persona = personaService.getPersona(id);
        return persona.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para crear una nueva persona.
     * Recibe los datos de la nueva persona en el cuerpo de la petición y la guarda en la base de datos.
     *
     * @param persona El objeto {@link Persona} con los datos de la nueva persona.
     * @return Una respuesta {@link ResponseEntity} con la persona creada y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear persona")
    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody Persona persona) {
        Persona nuevaPersona = personaService.createPersona(persona);
        return new ResponseEntity<>(nuevaPersona, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de una persona existente.
     * Recibe el ID de la persona a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id                  El identificador único de la persona a actualizar.
     * @param personaActualizada El objeto {@link Persona} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con la persona actualizada y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la persona a actualizar.
     */
    @Operation(summary = "Actualizar una persona por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Persona> updatePersona(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        try {
            Persona persona = personaService.updatePersona(id, personaActualizada);
            return new ResponseEntity<>(persona, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar una persona por su ID.
     * Recibe el ID de la persona a eliminar en la ruta.
     *
     * @param id El identificador único de la persona a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra la persona a eliminar.
     */
    @Operation(summary = "Eliminar una persona por su ID")
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