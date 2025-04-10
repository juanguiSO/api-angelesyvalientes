package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Angel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de repositorio de Spring Data JPA para entidades {@link Angel}.
 *
 * <p>Esta interfaz extiende {@link JpaRepository}, proporcionando operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) estándar para la entidad {@link Angel}.
 * Las operaciones de persistencia subyacentes son gestionadas por la implementación
 * de Spring Data JPA.</p>
 *
 * <p>El repositorio gestiona entidades {@link Angel}, donde la clave primaria
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
 * @see Angel La clase de entidad gestionada por este repositorio. Representa a un "Ángel" en el sistema.
 * @see JpaRepository La interfaz base de Spring Data JPA que proporciona la funcionalidad CRUD.
 * @see Repository La anotación de Spring que marca esta interfaz como un componente de repositorio.
 */
@Repository
public interface AngelRepository extends JpaRepository<Angel, Long> {

    // Aún no se han definido métodos de consulta personalizados aquí.
    // Spring Data JPA proporciona implementaciones para métodos como:
    // - save(Angel entity) -> guardar
    // - findById(Long id) -> buscarPorId
    // - findAll() -> buscarTodos
    // - deleteById(Long id) -> borrarPorId
    // - etc.

}