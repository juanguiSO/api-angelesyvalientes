package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz de repositorio de Spring Data JPA para entidades {@link Donacion}.
 *
 * <p>Esta interfaz extiende {@link JpaRepository}, proporcionando operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) estándar para la entidad {@link Donacion}.
 * Las operaciones de persistencia subyacentes son gestionadas por la implementación
 * de Spring Data JPA.</p>
 *
 * <p>El repositorio gestiona entidades {@link Donacion}, donde la clave primaria
 * es de tipo {@link Integer}.</p> // <--- CORREGIDO
 *
 * <p>Se recomienda anotar esta interfaz con {@link Repository} para que Spring
 * la detecte automáticamente durante el escaneo de componentes, cree un bean proxy
 * que implemente estos métodos y habilite la traducción de excepciones específicas
 * de la persistencia a excepciones de Spring.</p>
 *
 * <p>Se pueden añadir métodos de consulta personalizados definiendo firmas de método
 * que sigan las convenciones de nomenclatura de Spring Data JPA o utilizando
 * la anotación {@code @Query}.</p>
 *
 * @see Donacion La clase de entidad gestionada por este repositorio, representando una donación.
 * @see JpaRepository La interfaz base de Spring Data JPA que proporciona la funcionalidad CRUD.
 * @see Repository La anotación de Spring que marca esta interfaz como un componente de repositorio (recomendada).
 */
@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Integer> { // <--- CORREGIDO: Integer

    /**
     * Busca y devuelve una lista de donaciones asociadas a una Persona específica
     * por su ID. Spring Data JPA construirá la consulta JPQL/SQL automáticamente
     * basándose en el nombre del método.
     *
     * El nombre del método sigue el patrón findBy[NombreDeLaPropiedadDeRelacion]_[NombreDelCampoIdEnLaEntidadRelacionada].
     * En este caso, 'Persona' es el nombre de la propiedad en la entidad Donacion, y
     * 'NmIdPersona' es el nombre del campo ID en la entidad Persona.
     *
     * @param nmIdPersona El ID (nm_id_persona) de la persona por la cual se desean listar las donaciones.
     * @return Una lista de objetos Donacion asociados al ID de Persona dado.
     */
    List<Donacion> findByPersona_NmIdPersona(int nmIdPersona);
}