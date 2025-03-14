package com.practica.angelesyvalientes.service;


import com.practica.angelesyvalientes.entity.Usuario;
import com.practica.angelesyvalientes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    // Obtener un usuario por su ID
    public Optional<Usuario> getUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    // Obtener todos los usuarios
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    // Guardar un nuevo usuario
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Actualizar un usuario existente
    public Usuario updateUsuario(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();

            // Actualizar los campos
            usuario.setPersona(usuarioActualizado.getPersona());
            usuario.setTxContrasena(usuarioActualizado.getTxContrasena());
            usuario.setFeCreacion(usuarioActualizado.getFeCreacion());

            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuario con ID " + id + " no encontrado.");
        }
    }

    // Eliminar un usuario por su ID
    public void deleteUsuario(Long id) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            usuarioRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("Usuario con ID " + id + " no encontrado.");
        }
    }

    public boolean verificarCredenciales(Usuario usuarioRequest) {
        // Buscar usuario por Codigo del usuario
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCodigo(usuarioRequest.getCdUsuario());

        // Validar si el usuario existe y si la contraseña coincide
        if (usuarioOpt.isPresent()) {
           Usuario usuario = usuarioOpt.get();
            return usuario.getTxContrasena().equals(usuarioRequest.getTxContrasena());
        } else {
            return false; // Usuario no encontrado
        }
    }

}
