package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfaz de repositorio de Spring Data JPA para entidades {@link Usuario}.
 *
 * <p>Esta interfaz extiende {@link JpaRepository}, proporcionando operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) estándar para la entidad {@link Usuario}.
 * Las operaciones de persistencia subyacentes son gestionadas por la implementación
 * de Spring Data JPA.</p>
 *
 * <p>El repositorio gestiona entidades {@link Usuario}, donde la clave primaria
 * es de tipo {@link Long}.</p>
 *
 * <p>Spring detectará automáticamente esta interfaz gracias a la anotación {@link Repository}
 * y creará un bean proxy que implementa estos métodos. La anotación también habilita
 * la traducción de excepciones específicas de la persistencia a excepciones de Spring.</p>
 *
 * <p>Además de las operaciones CRUD estándar, esta interfaz define métodos de consulta
 * personalizados utilizando la anotación {@link Query} y las convenciones de nomenclatura
 * de Spring Data JPA.</p>
 *
 * @see Usuario La clase de entidad gestionada por este repositorio, representando un usuario del sistema.
 * @see Persona La entidad relacionada que contiene información personal como el correo electrónico.
 * @see JpaRepository La interfaz base de Spring Data JPA que proporciona la funcionalidad CRUD.
 * @see Repository La anotación de Spring que marca esta interfaz como un componente de repositorio.
 * @see Query La anotación para definir consultas JPQL personalizadas.
 * @see Param La anotación para enlazar parámetros de método a parámetros de consulta.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario basado en la dirección de correo electrónico asociada a su entidad {@link Persona}.
     * Utiliza una consulta JPQL personalizada.
     *
     * @param correo La dirección de correo electrónico (txCorreo en Persona) a buscar.
     * @return Un {@link Optional} que contiene el {@link Usuario} si se encuentra uno asociado
     *         a ese correo electrónico, o un {@link Optional} vacío si no existe.
     */
    @Query("SELECT u FROM Usuario u WHERE u.persona.txCorreo = :correo")
    Optional<Usuario> findByPersonaCorreo(@Param("correo") String correo);

    /**
     * Busca un usuario por su código de usuario.
     *
     * @param cdUsuario El código único del usuario.
     * @return Un Optional que contiene el Usuario si se encuentra, o un Optional vacío si no.
     */
    Optional<Usuario> findByCdUsuario(String cdUsuario);


    /**
     * Busca un usuario específico por su código de usuario (cdUsuario).
     * Utiliza una consulta JPQL personalizada.
     *
     * @param usuario El código de usuario (cdUsuario) a buscar.
     * @return Un {@link Optional} que contiene el {@link Usuario} si se encuentra uno con ese código,
     *         o un {@link Optional} vacío si no existe.
     */
    @Query("SELECT u FROM Usuario u WHERE u.cdUsuario = :usuario")
    Optional<Usuario> findByCodigo(@Param("usuario") String usuario);

    /**
     * Verifica si existe un usuario con el código de usuario (cdUsuario) especificado.
     * Este método utiliza la convención de nombres de Spring Data JPA para generar la consulta.
     *
     * @param cdUsuario El código de usuario (cdUsuario) cuya existencia se desea verificar.
     * @return {@code true} si existe un usuario con ese código, {@code false} en caso contrario.
     */
    boolean existsByCdUsuario(String cdUsuario);
}