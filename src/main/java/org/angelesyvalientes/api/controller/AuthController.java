package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Usuario;
import org.angelesyvalientes.api.persistence.repository.UsuarioRepository;
import org.angelesyvalientes.api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autorización")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;

    @Operation(summary = "Login para obtener token de sesión")
    @PostMapping("/login")
    public String authenticateUser(@RequestBody Usuario usuario) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuario.getCdUsuario(),
                        usuario.getTxContrasena()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }

    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping("/signup")
    public String registerUser(@RequestBody Usuario usuario) {
        if (userRepository.existsByCdUsuario(usuario.getCdUsuario())) {
            return "Error: El nombre de usuario no está disponible";
        }

        // Crear nueva cuenta de usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCdUsuario(usuario.getCdUsuario());
        nuevoUsuario.setTxContrasena(encoder.encode(usuario.getTxContrasena()));

        userRepository.save(nuevoUsuario);
        return "Usuario registrado correctamente!";
    }
}
