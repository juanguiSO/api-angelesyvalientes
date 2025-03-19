package com.practica.angelesyvalientes.service;

import com.practica.angelesyvalientes.entity.Usuario;
import com.practica.angelesyvalientes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    private UsuarioRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = userRepository.findByCodigo(username);

        if (!usuarioOpt.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado por código de usuario: " + username);
        }

        Usuario usuario = usuarioOpt.get();

        return new org.springframework.security.core.userdetails.User(
                usuario.getCdUsuario(),
                usuario.getTxContrasena(),
                Collections.emptyList()
        );
    }
}
