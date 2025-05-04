package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Educacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio de Spring Data JPA para entidades {@link Educacion}.
 *
 * <p>Esta interfaz extiende {@link JpaRepository}, proporcionando operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) estándar para la entidad {@link Educacion}.
 * Las operaciones de persistencia subyacentes son gestionadas por la implementación
 * de Spring Data JPA.</p>
 *
 * <p>El repositorio gestiona entidades {@link Educacion}, donde la clave primaria
 * es de tipo {@link Long}.</p>
 *
 * <p>Spring detectará automáticamente esta interfaz gracias a la anotación {@link Repository}
 * y creará un bean proxy que implementa estos métodos. La anotación también habilita
 * la traducción de excepciones específicas de la persistencia a excepciones de Spring.</p>
 *
 * <p>Se pueden añadir métodos de consulta personalizados definiendo firmas de método
 * que sigan las convenciones de nomenclatura de Spring Data JPA o utilizando
 * la anotación {@code @Query}.</p>
 *
 * @see Educacion La clase de entidad gestionada por este repositorio, representando información relacionada con la educación.
 * @see JpaRepository La interfaz base de Spring Data JPA que proporciona la funcionalidad CRUD.
 * @see Repository La anotación de Spring que marca esta interfaz como un componente de repositorio.
 */
@Repository
public interface EducacionRepository extends JpaRepository<Educacion, Long> {

    List<Educacion> findByPersona_NmIdPersona(Long personaId);

}