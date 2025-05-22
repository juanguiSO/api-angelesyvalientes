package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.dto.ActualizarContrasenaRequestDTO;
import org.angelesyvalientes.api.dto.UsuarioRequestDTO;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.persistence.entity.Rol;
import org.angelesyvalientes.api.persistence.entity.Usuario;
import org.angelesyvalientes.api.persistence.repository.PersonaRepository;
import org.angelesyvalientes.api.persistence.repository.RolRepository;
import org.angelesyvalientes.api.persistence.repository.UsuarioRepository;
import org.angelesyvalientes.api.security.JwtUtil;
import org.angelesyvalientes.api.service.CodigoVerificacionService;
import org.angelesyvalientes.api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.Optional;

@Tag(name = "Autorización")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioRepository userRepository;


    @Autowired
    private CodigoVerificacionService codigoVerificacionService;

    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    RolRepository rolRepository;

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



    @Operation(summary = "Verificar si el usuario existe y enviar código al correo")
    @PostMapping("/verificar-usuario")
    public ResponseEntity<String> verificarUsuario(@RequestBody Usuario usuario) {
        return userRepository.findByCdUsuario(usuario.getCdUsuario())
                .map(existingUsuario -> {
                    if (existingUsuario.getPersona() != null && existingUsuario.getPersona().getTxCorreo() != null && !existingUsuario.getPersona().getTxCorreo().isEmpty()) {
                        // Generar código de 4 dígitos
                        String codigo = codigoVerificacionService.generarCodigo();

                        // Guardar el código en la base de datos
                        codigoVerificacionService.guardarCodigo(existingUsuario.getCdUsuario(), codigo);

                        // Enviar el código por correo electrónico
                        try {
                            // Asumiendo que EmailService es un bean gestionado por Spring
                            // y puedes autoinyectarlo.
                            EmailService emailService = new EmailService(); // Esto es un antipatrón, debería ser inyectado
                            emailService.enviarCodigo(existingUsuario.getPersona().getTxCorreo(), codigo);
                            System.out.println("Código de verificación enviado a " + existingUsuario.getPersona().getTxCorreo() + ": " + codigo);
                            return new ResponseEntity<>("Se ha enviado un código de verificación a su correo electrónico.", HttpStatus.OK);
                        } catch (MessagingException e) {
                            System.err.println("Error al enviar el correo: " + e.getMessage());
                            return new ResponseEntity<>("Error al enviar el correo electrónico.", HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        return new ResponseEntity<>("El usuario no tiene un correo electrónico registrado.", HttpStatus.NOT_FOUND);
                    }
                })
                .orElseGet(() -> new ResponseEntity<>("El usuario no existe.", HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioRequestDTO usuarioDTO) {
        // 1. Validar la entrada (UsuarioRequestDTO)
        if (usuarioDTO == null) {
            return ResponseEntity.badRequest().body("Error: Los datos del usuario no pueden ser nulos.");
        }

        if (usuarioDTO.getCdUsuario() == null || usuarioDTO.getCdUsuario().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: El nombre de usuario es obligatorio.");
        }

        if (usuarioDTO.getTxContrasena() == null || usuarioDTO.getTxContrasena().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: La contraseña es obligatoria.");
        }

        if (usuarioDTO.getNmIdPersona() == null) {
            return ResponseEntity.badRequest().body("Error: El ID de la persona asociada es obligatorio.");
        }

        if (usuarioDTO.getIdRol() == null) {
            return ResponseEntity.badRequest().body("Error: El ID del rol es obligatorio.");
        }

        // 2. Verificar la existencia de recursos relacionados
        if (userRepository.existsByCdUsuario(usuarioDTO.getCdUsuario())) {
            return ResponseEntity.badRequest().body("Error: El nombre de usuario no está disponible.");
        }

        // CORRECCIÓN: Convertir Long a Integer para findById del PersonaRepository
        Optional<Persona> personaOpt = personaRepository.findById(usuarioDTO.getNmIdPersona().intValue());
        if (personaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: La persona con ID " + usuarioDTO.getNmIdPersona() + " no existe.");
        }

        Optional<Rol> rolOptional = rolRepository.findById(usuarioDTO.getIdRol());
        if (rolOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: El rol con ID " + usuarioDTO.getIdRol() + " no existe.");
        }

        // 3. Crear la nueva entidad Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCdUsuario(usuarioDTO.getCdUsuario());
        nuevoUsuario.setTxContrasena(encoder.encode(usuarioDTO.getTxContrasena()));
        nuevoUsuario.setPersona(personaOpt.get());
        nuevoUsuario.setRol(rolOptional.get()); // Asignar el rol encontrado
        nuevoUsuario.setFeCreacion(LocalDate.now());
        nuevoUsuario.setDeleted(false);

        // 4. Guardar el nuevo usuario en la base de datos
        try {
            userRepository.save(nuevoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario: " + e.getMessage());
        }
    }

    @Operation(summary = "Actualizar la contraseña del usuario con código de verificación")
    @PostMapping("/actualizar-contrasena")
    public ResponseEntity<String> actualizarContrasena(@RequestBody ActualizarContrasenaRequestDTO request) {
        if (request.getCdUsuario() == null || request.getCdUsuario().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: El nombre de usuario es obligatorio.");
        }

        if (request.getCodigoVerificacion() == null || request.getCodigoVerificacion().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: El código de verificación es obligatorio.");
        }

        if (request.getNuevaContrasena() == null || request.getNuevaContrasena().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: La nueva contraseña es obligatoria.");
        }

        // Buscar al usuario por cdUsuario
        Optional<Usuario> usuarioOpt = userRepository.findByCdUsuario(request.getCdUsuario());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: El usuario no existe.");
        }

        Usuario usuario = usuarioOpt.get();

        // Validar el código de verificación utilizando el servicio
        if (!codigoVerificacionService.esCodigoValido(request.getCdUsuario(), request.getCodigoVerificacion())) {
            return ResponseEntity.badRequest().body("Error: El código de verificación es incorrecto o ha expirado.");
        }

        // Actualizar la contraseña cifrada
        usuario.setTxContrasena(encoder.encode(request.getNuevaContrasena()));
        try {
            userRepository.save(usuario);
            // Eliminar el código de verificación después de su uso exitoso
            codigoVerificacionService.eliminarCodigo(request.getCdUsuario());
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la contraseña: " + e.getMessage());
        }
    }
}
