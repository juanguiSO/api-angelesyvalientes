package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Angel;
import org.angelesyvalientes.api.persistence.repository.PersonaRepository;
import org.angelesyvalientes.api.service.AngelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Angel}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar ángeles.
 * La API está etiquetada como "Angeles" en la documentación de Swagger.
 */
@Tag(name = "Angeles")
@RestController
@RequestMapping("/api/angeles")
public class AngelController {

    private final AngelService angelService;
    @Autowired
    private PersonaRepository personaRepository;

    /**
     * Constructor de la clase {@code AngelController}.
     * Recibe una instancia de {@link AngelService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con los ángeles.
     *
     * @param angelService El servicio para la gestión de ángeles.
     */
    @Autowired
    public AngelController(AngelService angelService) {
        this.angelService = angelService;
    }

    /**
     * Endpoint para listar todos los ángeles.
     * Retorna una lista de todos los ángeles almacenados en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de ángeles y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todas los Angeles")
    @GetMapping
    public List<Angel> getAll() {
        return angelService.findAll();
    }

    /**
     * Endpoint para obtener un ángel por su ID.
     * Retorna un ángel específico basado en el ID proporcionado en la ruta.
     *
     * @param id El identificador único del ángel a buscar.
     * @return Una respuesta {@link ResponseEntity} con el ángel encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el ángel.
     */
    @Operation(summary = "Obtener un Angel por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Angel> getById(@PathVariable Long id) {
        Angel angel = angelService.findById(id);
        if (angel != null) {
            return ResponseEntity.ok(angel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para crear un nuevo ángel.
     * Recibe los datos del nuevo ángel en el cuerpo de la petición y lo guarda en la base de datos.
     *
     * @param angel El objeto {@link Angel} con los datos del nuevo ángel.
     * @return Una respuesta {@link ResponseEntity} con el ángel creado y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear un Angel ")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Angel angel) {
        // Verifica si la persona existe
        // CORRECCIÓN: Convertir Long a Integer para existsById del PersonaRepository
        boolean personaExiste = personaRepository.existsById(angel.getIdPersona().intValue());

        if (!personaExiste) {
            return ResponseEntity.badRequest().body("La persona con ID " + angel.getIdPersona() + " no existe.");
        }

        // Si todo está bien, guarda el ángel
        Angel nuevoAngel = angelService.save(angel);
        return ResponseEntity.ok(nuevoAngel);
    }

    /**
     * Endpoint para actualizar la información de un ángel existente.
     * Recibe el ID del ángel a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id          El identificador único del ángel a actualizar.
     * @param updatedAngel El objeto {@link Angel} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con el ángel actualizado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el ángel a actualizar.
     */
    @Operation(summary = "Actualizar una Angel por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Angel> update(@PathVariable Long id, @RequestBody Angel updatedAngel) {
        Angel existingAngel = angelService.findById(id);
        if (existingAngel != null) {
            updatedAngel.setIdPersona(id);
            return ResponseEntity.ok(angelService.save(updatedAngel));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para eliminar un ángel por su ID.
     * Recibe el ID del ángel a eliminar en la ruta.
     *
     * @param id El identificador único del ángel a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa.
     */
    @Operation(summary = "Eliminar una Angel  por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Angel angel = angelService.findById(id);
        if (angel != null) {
            angelService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getAngelesCount() {
        long count = angelService.countAllAngeles();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

}
