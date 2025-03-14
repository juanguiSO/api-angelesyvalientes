package com.practica.angelesyvalientes.controller;

import com.practica.angelesyvalientes.entity.Usuario;
import com.practica.angelesyvalientes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.getUsuario(id);

        if (usuario.isPresent()) {
            return new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        try {
            Usuario usuario = usuarioService.updateUsuario(id, usuarioActualizado);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un usuario por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear un nuevo usuario  hacer que este metodo valide en usuario service si el usuario y contraseña estan registrados
    //
    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody Usuario usuario) {
        // Validar que el usuario y la contraseña no esten vacios
        if(usuario.getCdUsuario() == null || usuario.getCdUsuario().equals("")){
            return new ResponseEntity<>("El usuario es obligatorio", HttpStatus.NOT_FOUND);
        }

        if(usuario.getTxContrasena() == null || usuario.getTxContrasena().equals("")){
            return new ResponseEntity<>("La clave es obligatoria",HttpStatus.NOT_FOUND);
        }

        boolean autorized = usuarioService.verificarCredenciales(usuario);

        //Informacion del usuario si esta la informacion y si no un error
        if (autorized){
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Usuario o clave invalida",HttpStatus.FORBIDDEN);
        }
    }
}
