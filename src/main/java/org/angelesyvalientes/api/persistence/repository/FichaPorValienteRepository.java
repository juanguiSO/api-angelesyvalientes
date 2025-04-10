package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.FichaPorValiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz de repositorio de Spring Data JPA para entidades {@link FichaPorValiente}.
 *
 * <p>Esta interfaz extiende {@link JpaRepository}, proporcionando operaciones CRUD
 * (Crear, Leer, Actualizar, Borrar) estándar para la entidad {@link FichaPorValiente}.
 * Las operaciones de persistencia subyacentes son gestionadas por la implementación
 * de Spring Data JPA.</p>
 *
 * <p>El repositorio gestiona entidades {@link FichaPorValiente}, donde la clave primaria
 * es de tipo {@link Long}. Esta entidad representa la ficha asociada a cada "Valiente".</p>
 *
 * <p>Spring detectará automáticamente esta interfaz gracias a la anotación {@link Repository}
 * y creará un bean proxy que implementa estos métodos. La anotación también habilita
 * la traducción de excepciones específicas de la persistencia a excepciones de Spring.</p>
 *
 * <p>Se pueden añadir métodos de consulta personalizados definiendo firmas de método
 * que sigan las convenciones de nomenclatura de Spring Data JPA o utilizando
 * la anotación {@code @Query}.</p>
 *
 * @see FichaPorValiente La clase de entidad gestionada por este repositorio, representando la ficha de un "Valiente".
 * @see JpaRepository La interfaz base de Spring Data JPA que proporciona la funcionalidad CRUD.
 * @see Repository La anotación de Spring que marca esta interfaz como un componente de repositorio.
 */
@Repository
public interface FichaPorValienteRepository extends JpaRepository<FichaPorValiente, Long> {

    // Aún no se han definido métodos de consulta personalizados aquí.
    // Spring Data JPA proporciona implementaciones para métodos como:
    // - save(FichaPorValiente entity) -> guardar
    // - findById(Long id) -> buscarPorId
    // - findAll() -> buscarTodos
    // - deleteById(Long id) -> borrarPorId
    // - etc.

}