package org.angelesyvalientes.api.security;

import org.angelesyvalientes.api.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de Spring Web que se ejecuta una vez por cada petición entrante.
 * Su función principal es interceptar las peticiones HTTP, extraer el token JWT (si existe)
 * del encabezado de autorización, validar el token y, si es válido, autenticar al usuario
 * configurando el {@link SecurityContextHolder} con la información del usuario extraída del token.
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    /**
     * Servicio utilitario para la manipulación y validación de tokens JWT.
     */
    @Autowired
    private JwtUtil jwtUtils;

    /**
     * Servicio personalizado para cargar los detalles del usuario desde la base de datos
     * basado en el nombre de usuario extraído del token JWT.
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Intercepta cada petición HTTP y realiza la lógica de autenticación basada en JWT.
     *
     * @param request     La petición HTTP entrante.
     * @param response    La respuesta HTTP saliente.
     * @param filterChain El siguiente filtro en la cadena de filtros.
     * @throws ServletException Si ocurre un error específico del servlet durante el procesamiento del filtro.
     * @throws IOException      Si ocurre un error de entrada/salida durante el procesamiento del filtro.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            // Intenta extraer el token JWT de la petición.
            String jwt = parseJwt(request);
            // Si el token existe y es válido...
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Extrae el nombre de usuario del token.
                String username = jwtUtils.getUsernameFromToken(jwt);
                // Carga los detalles del usuario utilizando el servicio personalizado.
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Crea un objeto de autenticación (sin credenciales) con los detalles del usuario y sus autoridades.
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                // Establece los detalles de la autenticación basados en la petición web.
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Establece la autenticación en el contexto de seguridad, indicando que el usuario está autenticado para esta petición.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Imprime un mensaje de error en caso de que no sea posible configurar la autenticación.
            System.out.println("No es posible configurar la autenticación al usuario: " + e);
        }
        // Continúa la cadena de filtros, permitiendo que la petición siga su curso.
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT del encabezado de autorización de la petición.
     * El encabezado debe tener el formato "Bearer [token]".
     *
     * @param request La petición HTTP entrante.
     * @return El token JWT si se encuentra en el encabezado y tiene el formato correcto, o null si no se encuentra o el formato es incorrecto.
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Elimina el prefijo "Bearer " para obtener solo el token.
        }
        return null;
    }
}