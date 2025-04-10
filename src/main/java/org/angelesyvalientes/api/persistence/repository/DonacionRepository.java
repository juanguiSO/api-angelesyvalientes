package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Importar la anotación Repository

/**
 * Interfaz de repositorio de Spring Data JPA para entidades {@link Donacion}.
 *
 * <p>Esta interfaz extiende {@link JpaRepository}, proporcionando operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) estándar para la entidad {@link Donacion}.
 * Las operaciones de persistencia subyacentes son gestionadas por la implementación
 * de Spring Data JPA.</p>
 *
 * <p>El repositorio gestiona entidades {@link Donacion}, donde la clave primaria
 * es de tipo {@link Long}.</p>
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
@Repository // Añadir la anotación @Repository es una buena práctica
public interface DonacionRepository extends JpaRepository<Donacion, Long>  {

    // Aún no se han definido métodos de consulta personalizados aquí.
    // Spring Data JPA proporciona implementaciones para métodos como:
    // - save(Donacion entity) -> guardar
    // - findById(Long id) -> buscarPorId
    // - findAll() -> buscarTodos
    // - deleteById(Long id) -> borrarPorId
    // - etc.

}