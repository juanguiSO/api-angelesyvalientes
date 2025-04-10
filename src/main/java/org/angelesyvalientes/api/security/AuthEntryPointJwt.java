package org.angelesyvalientes.api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Clase componente de Spring Security que implementa la interfaz {@link AuthenticationEntryPoint}.
 * Su función es manejar las peticiones no autenticadas que intentan acceder a recursos protegidos.
 * Cuando una petición sin la autenticación válida intenta acceder a un recurso seguro,
 * el método {@code commence} de esta clase es invocado.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    /**
     * Este método es llamado cuando una petición no autenticada intenta acceder a un recurso protegido.
     * Envía una respuesta de error HTTP 401 (No Autorizado) al cliente.
     *
     * @param request       La petición HTTP que originó la excepción de autenticación.
     * @param response      La respuesta HTTP que se enviará al cliente.
     * @param authException La excepción de autenticación que se produjo.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta de error.
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: No Autorizado");
    }
}