package org.angelesyvalientes.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de configuración de Spring que habilita la configuración de CORS (Cross-Origin Resource Sharing)
 * para la aplicación. CORS es un mecanismo de seguridad implementado por los navegadores
 * para restringir las solicitudes HTTP que un script que se ejecuta en un origen puede realizar
 * a recursos en otro origen.
 */
@Configuration
public class CorsConfig {

    /**
     * Define un bean de Spring del tipo {@link WebMvcConfigurer}. Este bean permite
     * personalizar la configuración de MVC en Spring, incluyendo la configuración de CORS.
     *
     * @return Una instancia de {@link WebMvcConfigurer} con la configuración de CORS.
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Sobrescribe el método {@code addCorsMappings} de la interfaz {@link WebMvcConfigurer}.
             * Este método permite configurar las reglas de CORS para la aplicación.
             *
             * @param registry Un {@link CorsRegistry} que proporciona métodos para configurar los mappings de CORS.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                /**
                 * Agrega un mapping de CORS para todas las rutas de la aplicación ("/**").
                 * Por defecto, al usar {@code registry.addMapping("/**")}, se permiten todos los orígenes,
                 * todos los métodos HTTP (GET, POST, PUT, DELETE, etc.) y se habilitan las credenciales
                 * (como cookies y encabezados de autorización).
                 *
                 * Para una configuración más restrictiva, se pueden utilizar los métodos del {@link CorsRegistry.CorsRegistration}
                 * devuelto por {@code addMapping()}, como:
                 * - {@code allowedOrigins("http://dominio1.com", "http://dominio2.com")}: Para especificar los orígenes permitidos.
                 * - {@code allowedMethods("GET", "POST")}: Para especificar los métodos HTTP permitidos.
                 * - {@code allowedHeaders("Content-Type", "Authorization")}: Para especificar los encabezados permitidos.
                 * - {@code exposedHeaders("Custom-Header")}: Para especificar los encabezados que se expondrán en la respuesta.
                 * - {@code allowCredentials(true)}: Para permitir el envío de credenciales.
                 * - {@code maxAge(3600)}: Para especificar la duración máxima (en segundos) durante la cual los resultados de una solicitud de preflight pueden ser almacenados en caché.
                 */
                registry.addMapping("/**");
            }
        };
    }
}