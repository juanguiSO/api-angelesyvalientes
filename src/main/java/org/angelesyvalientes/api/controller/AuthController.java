package org.angelesyvalientes.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.angelesyvalientes.api.persistence.entity.Usuario;
import org.angelesyvalientes.api.persistence.repository.UsuarioRepository;
import org.angelesyvalientes.api.security.JwtUtil;
import org.angelesyvalientes.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io. swagger. v3.oas. annotations. StringToClassMapItem;

import java.util.Map;

/**
 * Controlador REST para la gestión de la autenticación y registro de usuarios.
 * Expone endpoints para el inicio de sesión (login) y la creación de nuevas cuentas de usuario (signup).
 * La API está etiquetada como "Autorización" en la documentación de Swagger.
 */
@Tag(name = "Autorización")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtils;
    private final UsuarioService usuarioService;

    /**
     * Constructor de la clase {@code AuthController}.
     * Recibe las dependencias necesarias para la autenticación y la gestión de usuarios
     * a través de la inyección de dependencias.
     *
     * @param authenticationManager El gestor de autenticación de Spring Security.
     * @param userRepository      El repositorio para acceder a los datos de los usuarios.
     * @param encoder             El codificador de contraseñas.
     * @param jwtUtils            La utilidad para la generación de tokens JWT.
     * @param usuarioService      El servicio para la gestión de usuarios.
     */
    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UsuarioRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtils, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint para la autenticación de usuarios y la obtención de un token JWT.
     * Recibe las credenciales del usuario (nombre de usuario y contraseña) en el cuerpo de la petición.
     * Si la autenticación es exitosa, genera un token JWT para el usuario.
     *
     * @param usuario El objeto {@link Usuario} que contiene el nombre de usuario (cdUsuario) y la contraseña (txContrasena).
     * @return Una cadena que representa el token JWT generado para el usuario autenticado.
     */
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

    /**
     * Endpoint para registrar un nuevo usuario en el sistema.
     * Recibe los datos del nuevo usuario en el cuerpo de la petición.
     * Verifica si el nombre de usuario ya existe. Si no existe, crea una nueva cuenta de usuario
     * con la contraseña codificada y la guarda en la base de datos.
     *
     * @param usuario El objeto {@link Usuario} que contiene el nombre de usuario (cdUsuario) y la contraseña (txContrasena)
     * para el nuevo usuario.
     * @return Una cadena que indica el resultado del registro: "Error: El nombre de usuario no está disponible"
     * si el nombre de usuario ya existe, o "Usuario registrado correctamente!" si el registro fue exitoso.
     */
    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping("/signup")
    public String registerUser(@RequestBody Usuario usuario) {
        if (userRepository.existsByCdUsuario(usuario.getCdUsuario())) {
            return "Error: El nombre de usuario no está disponible";
        }
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setCdUsuario(usuario.getCdUsuario());
        nuevoUsuario.setTxContrasena(encoder.encode(usuario.getTxContrasena()));
        userRepository.save(nuevoUsuario);
        return "Usuario registrado correctamente!";
    }
/*
    @Operation(
            summary = "Actualizar contraseña olvidada de un usuario",
            description = "Permite a un usuario actualizar su contraseña proporcionando su código de usuario y la nueva contraseña.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Contraseña actualizada correctamente."),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado.", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida (ej., contraseña vacía).", content = @Content)
            }
    )
    @PutMapping("/forgot-password/{cdUsuario}")
    public ResponseEntity<String> updateForgotPassword(
            @Parameter(description = "Código único del usuario a actualizar.", required = true) @PathVariable String cdUsuario,
            @RequestBody(
                    description = "Objeto JSON conteniendo la nueva contraseña.",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    properties = {
                                            @SchemaProperty(name = "nuevaContrasena", schema = @Schema(type = "string", example = "NuevaSegura123"))
                                    }
                            )
                    )
            )
            @Parameter(hidden = true) Map<String, String> nuevaContrasena // Ya no necesitamos @Schema aquí
    ) {
        String nuevaPass = nuevaContrasena.get("nuevaContrasena");

        if (nuevaPass == null || nuevaPass.trim().isEmpty()) {
            return new ResponseEntity<>("La nueva contraseña no puede estar vacía.", HttpStatus.BAD_REQUEST);
        }

        if (usuarioService.updatePassword(cdUsuario, nuevaPass)) {
            return new ResponseEntity<>("Contraseña actualizada correctamente.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado.", HttpStatus.NOT_FOUND);
        }
    }*/
}