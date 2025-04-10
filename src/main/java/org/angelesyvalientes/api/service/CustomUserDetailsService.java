package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Usuario;
import org.angelesyvalientes.api.persistence.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * Servicio personalizado que implementa la interfaz {@link UserDetailsService} de Spring Security.
 * Su función principal es cargar los detalles de un usuario ({@link UserDetails})
 * basándose en su nombre de usuario (en este caso, el código de usuario) para la autenticación.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Repositorio para acceder a los datos de la entidad {@link Usuario} en la base de datos.
     */
    @Autowired
    private UsuarioRepository userRepository;

    /**
     * Carga los detalles de un usuario por su nombre de usuario (código de usuario).
     * Este método es utilizado por Spring Security durante el proceso de autenticación
     * para obtener la información del usuario.
     *
     * @param username El nombre de usuario (código de usuario) del usuario a cargar.
     * @return Un objeto {@link UserDetails} que contiene la información del usuario para la autenticación.
     * @throws UsernameNotFoundException Si no se encuentra ningún usuario con el nombre de usuario proporcionado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca un usuario en la base de datos por su código de usuario.
        Optional<Usuario> usuarioOpt = userRepository.findByCodigo(username);

        // Si no se encuentra ningún usuario con el código proporcionado, lanza una excepción.
        if (!usuarioOpt.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado por código de usuario: " + username);
        }

        // Obtiene la entidad Usuario del Optional.
        Usuario usuario = usuarioOpt.get();

        // Crea y devuelve un objeto UserDetails de Spring Security con la información del usuario.
        // En este caso, se utiliza el código de usuario como nombre de usuario, la contraseña
        // almacenada en la base de datos y una lista vacía de autoridades (roles/permisos).
        // La gestión de roles y permisos se podría implementar de manera más completa
        // cargando las autoridades del usuario desde sus roles asociados.
        return new org.springframework.security.core.userdetails.User(
                usuario.getCdUsuario(),
                usuario.getTxContrasena(),
                Collections.emptyList()
        );
    }
}