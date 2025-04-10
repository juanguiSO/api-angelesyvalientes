package org.angelesyvalientes.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Clase utilitaria de Spring que proporciona métodos para la generación,
 * extracción de información y validación de tokens JSON Web (JWT).
 * Utiliza la librería JJWT para realizar estas operaciones.
 */
@Component
public class JwtUtil {

    /**
     * Clave secreta utilizada para firmar el token JWT.
     * Este valor se inyecta desde la configuración de la aplicación.
     * Es crucial mantener esta clave en secreto para la seguridad de la aplicación.
     */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Tiempo de expiración del token JWT en milisegundos.
     * Este valor también se inyecta desde la configuración de la aplicación.
     */
    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    /**
     * Objeto {@link SecretKey} generado a partir de la clave secreta.
     * Se utiliza para firmar y verificar la firma del token JWT.
     */
    private SecretKey key;

    /**
     * Método de inicialización anotado con {@link PostConstruct}.
     * Se ejecuta una vez después de que el bean {@link JwtUtil} ha sido creado
     * e inicializadas sus dependencias.
     * Genera la {@link SecretKey} a partir de la clave secreta codificada en UTF-8
     * utilizando el algoritmo HMAC-SHA.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Genera un nuevo token JWT para el nombre de usuario proporcionado.
     * El token incluye el nombre de usuario como sujeto, la fecha de emisión,
     * la fecha de expiración (calculada a partir del tiempo de expiración configurado)
     * y está firmado con la clave secreta utilizando el algoritmo HS256.
     *
     * @param username El nombre de usuario para el que se generará el token.
     * @return El token JWT generado como una cadena.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Obtiene el nombre de usuario (sujeto) del token JWT proporcionado.
     * Parsea el token, verifica su firma utilizando la clave secreta y extrae
     * el valor del claim 'subject'.
     *
     * @param token El token JWT del que se extraerá el nombre de usuario.
     * @return El nombre de usuario extraído del token.
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valida la integridad y la validez del token JWT proporcionado.
     * Intenta parsear el token y verificar su firma utilizando la clave secreta.
     * Captura diferentes excepciones que pueden ocurrir durante la validación,
     * como firma inválida, token malformado, token expirado, token no soportado
     * o claims vacíos. Imprime mensajes de error descriptivos en caso de excepción.
     *
     * @param token El token JWT que se va a validar.
     * @return {@code true} si el token es válido y su firma es correcta, {@code false} en caso contrario.
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            System.out.println("Firma JWT inválida: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Token JWT inválido: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token expiró: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token no soportado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string está vacío: " + e.getMessage());
        }
        return false;
    }
}