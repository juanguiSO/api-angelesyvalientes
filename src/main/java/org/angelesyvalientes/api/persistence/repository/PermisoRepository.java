package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de repositorio de Spring Data JPA para entidades {@link Permiso}.
 *
 * <p>Esta interfaz extiende {@link JpaRepository}, proporcionando operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) estándar para la entidad {@link Permiso}.
 * Las operaciones de persistencia subyacentes son gestionadas por la implementación
 * de Spring Data JPA.</p>
 *
 * <p>El repositorio gestiona entidades {@link Permiso}, donde la clave primaria
 * es de tipo {@link Integer}.</p>
 *
 * <p>Spring detectará automáticamente esta interfaz gracias a la anotación {@link Repository}
 * y creará un bean proxy que implementa estos métodos. La anotación también habilita
 * la traducción de excepciones específicas de la persistencia a excepciones de Spring.</p>
 *
 * <p>Además de las operaciones CRUD estándar, esta interfaz define métodos de consulta
 * personalizados basados en las convenciones de nomenclatura de Spring Data JPA.</p>
 *
 * @see Permiso La clase de entidad gestionada por este repositorio, representando un permiso del sistema.
 * @see JpaRepository La interfaz base de Spring Data JPA que proporciona la funcionalidad CRUD.
 * @see Repository La anotación de Spring que marca esta interfaz como un componente de repositorio.
 */
@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

    /**
     * Busca todos los permisos asociados a un código de opción específico.
     *
     * @param codigoOpcion El código de la opción por la cual buscar permisos.
     * @return Una lista de entidades {@link Permiso} que coinciden con el código de opción;
     *         una lista vacía si no se encuentran coincidencias.
     */
    List<Permiso> findByCodigoOpcion(String codigoOpcion);

    /**
     * Busca todos los permisos asociados a un código de operación específico.
     *
     * @param codigoOperacion El código de la operación por la cual buscar permisos.
     * @return Una lista de entidades {@link Permiso} que coinciden con el código de operación;
     *         una lista vacía si no se encuentran coincidencias.
     */
    List<Permiso> findByCodigoOperacion(String codigoOperacion);

    /**
     * Busca un permiso específico basado en la combinación de código de opción y código de operación.
     * Se espera que esta combinación sea única o que solo interese el primer resultado encontrado.
     *
     * @param codigoOpcion El código de la opción del permiso.
     * @param codigoOperacion El código de la operación del permiso.
     * @return Un {@link Optional} que contiene el {@link Permiso} si se encuentra,
     *         o un {@link Optional} vacío si no existe un permiso con esa combinación.
     */
    Optional<Permiso> findByCodigoOpcionAndCodigoOperacion(String codigoOpcion, String codigoOperacion);

    /**
     * Busca todos los permisos que están marcados como activos.
     *
     * @return Una lista de entidades {@link Permiso} activas;
     *         una lista vacía si no hay permisos activos.
     */
    List<Permiso> findByActivoTrue();

    /**
     * Busca todos los permisos que están marcados como inactivos.
     *
     * @return Una lista de entidades {@link Permiso} inactivas;
     *         una lista vacía si no hay permisos inactivos.
     */
    List<Permiso> findByActivoFalse();
}