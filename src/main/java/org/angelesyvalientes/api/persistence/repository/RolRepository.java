package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de repositorio de Spring Data JPA para entidades {@link Rol}.
 *
 * <p>Esta interfaz extiende {@link JpaRepository}, proporcionando operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) estándar para la entidad {@link Rol}.
 * Las operaciones de persistencia subyacentes son gestionadas por la implementación
 * de Spring Data JPA.</p>
 *
 * <p>El repositorio gestiona entidades {@link Rol}, donde la clave primaria
 * es de tipo {@link Integer}.</p>
 *
 * <p>Spring detectará automáticamente esta interfaz gracias a la anotación {@link Repository}
 * y creará un bean proxy que implementa estos métodos. La anotación también habilita
 * la traducción de excepciones específicas de la persistencia a excepciones de Spring.</p>
 *
 * <p>Además de las operaciones CRUD estándar, esta interfaz define métodos de consulta
 * personalizados basados en las convenciones de nomenclatura de Spring Data JPA.</p>
 *
 * @see Rol La clase de entidad gestionada por este repositorio, representando un rol de usuario en el sistema.
 * @see JpaRepository La interfaz base de Spring Data JPA que proporciona la funcionalidad CRUD.
 * @see Repository La anotación de Spring que marca esta interfaz como un componente de repositorio.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    /**
     * Busca un rol específico por su nombre.
     * Se asume que el nombre del rol debe ser único en el sistema.
     *
     * @param nombre El nombre del rol a buscar.
     * @return Un {@link Optional} que contiene el {@link Rol} si se encuentra uno con el nombre especificado,
     *         o un {@link Optional} vacío si no existe.
     */
    Optional<Rol> findByNombre(String nombre);

    /**
     * Busca todos los roles que están marcados como activos.
     *
     * @return Una lista de entidades {@link Rol} activas;
     *         una lista vacía si no hay roles activos.
     */
    List<Rol> findByActivoTrue();

    /**
     * Busca todos los roles que están marcados como inactivos.
     *
     * @return Una lista de entidades {@link Rol} inactivas;
     *         una lista vacía si no hay roles inactivos.
     */
    List<Rol> findByActivoFalse();

}