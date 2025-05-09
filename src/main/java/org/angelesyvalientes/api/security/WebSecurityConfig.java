package org.angelesyvalientes.api.security;

import org.angelesyvalientes.api.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Clase de configuración de Spring Security que define las políticas de seguridad
 * para la aplicación. Configura la autenticación, la autorización, el manejo de excepciones
 * y la gestión de sesiones.
 */
@Configuration
public class WebSecurityConfig {

    /**
     * Servicio personalizado para cargar los detalles del usuario durante la autenticación.
     */
    @Autowired
    CustomUserDetailsService userDetailsService;

    /**
     * Punto de entrada para manejar las excepciones de autenticación no autorizadas.
     * Se utiliza cuando un usuario no autenticado intenta acceder a un recurso protegido.
     */
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    /**
     * Crea un bean del filtro de autenticación JWT. Este filtro intercepta las peticiones
     * y verifica la presencia y validez del token JWT en el encabezado de autorización.
     *
     * @return Una instancia de {@link AuthTokenFilter}.
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Crea un bean del {@link AuthenticationManager} utilizado para autenticar a los usuarios.
     * Se basa en la configuración de autenticación proporcionada.
     *
     * @param authenticationConfiguration La configuración de autenticación.
     * @return Una instancia del {@link AuthenticationManager}.
     * @throws Exception Si ocurre un error al obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Crea un bean del {@link PasswordEncoder} utilizado para codificar las contraseñas de los usuarios.
     * Se utiliza el algoritmo BCrypt, que es un algoritmo de hash seguro.
     *
     * @return Una instancia de {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define la fuente de configuración de CORS.
     *
     * @return La fuente de configuración de CORS.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Permitir todos los orígenes
        config.addAllowedHeader("*"); // Permitir todos los encabezados
        config.addAllowedMethod("*"); // Permitir todos los métodos
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Define la cadena de filtros de seguridad para las peticiones HTTP.
     * Configura las políticas de seguridad, como la desactivación de CSRF y CORS,
     * el manejo de excepciones de autenticación, la política de creación de sesiones
     * y las reglas de autorización para las diferentes rutas de la API.
     *
     * @param http El constructor {@link HttpSecurity} utilizado para configurar la seguridad HTTP.
     * @return La cadena de filtros de seguridad construida.
     * @throws Exception Si ocurre un error al configurar la seguridad HTTP.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactiva la protección contra ataques Cross-Site Request Forgery (CSRF), ya que la aplicación utiliza tokens JWT.
                .csrf(csrf -> csrf.disable())
                // Habilita CORS utilizando la configuración de CorsConfig.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Configura el manejo de excepciones para las fallas de autenticación.
                // Cuando un usuario no autenticado intenta acceder a un recurso protegido, se invoca el unauthorizedHandler.
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
                )
                // Configura la gestión de sesiones para que no se creen sesiones HTTP en el servidor.
                // La autenticación se basa en tokens JWT, que son stateless.
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Define las reglas de autorización para las peticiones HTTP.
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Permite el acceso sin autenticación a las rutas específicas.
                                .requestMatchers("/api/auth/login").permitAll() // Ruta para el inicio de sesión (obtención del token JWT).
                                .requestMatchers("/swagger-ui/**").permitAll() // Rutas para acceder a la documentación de Swagger UI.
                                .requestMatchers("/v3/api-docs/**").permitAll() // Rutas para acceder a la definición de la API en formato OpenAPI.
                                // Cualquier otra petición requiere autenticación.
                                .anyRequest().authenticated()
                );
        // Agrega el filtro de autenticación JWT antes del filtro estándar de autenticación por nombre de usuario y contraseña.
        // Esto asegura que el token JWT se verifique antes de intentar la autenticación basada en formularios o Basic Auth.
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}