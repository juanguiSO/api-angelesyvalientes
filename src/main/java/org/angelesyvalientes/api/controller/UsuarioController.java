package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Usuario;
import org.angelesyvalientes.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de {@link Usuario}.
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar usuarios.
 * La API está etiquetada como "Usuarios" en la documentación de Swagger y está marcada como Hidden,
 * lo que significa que no se mostrará en la documentación pública de la API.
 */
@Tag(name = "Usuarios")
@Hidden
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Constructor de la clase {@code UsuarioController}.
     * Recibe una instancia de {@link UsuarioService} a través de la inyección de dependencias
     * para manejar la lógica de negocio relacionada con los usuarios.
     *
     * @param usuarioService El servicio para la gestión de usuarios.
     */
    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para listar todos los usuarios.
     * Retorna una lista de todos los usuarios almacenados en la base de datos.
     *
     * @return Una respuesta {@link ResponseEntity} con la lista de usuarios y estado HTTP 200 (OK).
     */
    @Operation(summary = "Listar todos los usuarios")
    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }


    /**
     * Endpoint para obtener un usuario por su ID.
     * Retorna un usuario específico basado en el ID proporcionado en la ruta.
     *
     * @param id El identificador único del usuario a buscar.
     * @return Una respuesta {@link ResponseEntity} con el usuario encontrado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el usuario.
     */
    @Operation(summary = "Obtener un usuario por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.getUsuario(id);

        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para crear un nuevo usuario.
     * Recibe los datos del nuevo usuario en el cuerpo de la petición y lo guarda en la base de datos.
     *
     * @param usuario El objeto {@link Usuario} con los datos del nuevo usuario.
     * @return Una respuesta {@link ResponseEntity} con el usuario creado y estado HTTP 201 (CREATED).
     */
    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    /**
     * Endpoint para actualizar la información de un usuario existente.
     * Recibe el ID del usuario a actualizar en la ruta y los datos actualizados en el cuerpo de la petición.
     *
     * @param id              El identificador único del usuario a actualizar.
     * @param usuarioActualizado El objeto {@link Usuario} con los datos actualizados.
     * @return Una respuesta {@link ResponseEntity} con el usuario actualizado y estado HTTP 200 (OK),
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el usuario a actualizar.
     */
    @Operation(summary = "Actualizar un usuario por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        try {
            Usuario usuario = usuarioService.updateUsuario(id, usuarioActualizado);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint para eliminar un usuario por su ID.
     * Recibe el ID del usuario a eliminar en la ruta.
     *
     * @param id El identificador único del usuario a eliminar.
     * @return Una respuesta {@link ResponseEntity} con estado HTTP 204 (NO_CONTENT) si la eliminación fue exitosa,
     * o estado HTTP 404 (NOT_FOUND) si no se encuentra el usuario a eliminar.
     */
    @Operation(summary = "Eliminar un usuario por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}