package org.angelesyvalientes.api.persistence.repository;

import org.angelesyvalientes.api.persistence.entity.Ficha;
import org.angelesyvalientes.api.persistence.entity.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de repositorio de Spring Data JPA para entidades {@link Programa}.
 *
 * <p>Aunque el nombre de la interfaz es {@code FichaRepository}, esta gestiona
 * las operaciones CRUD (Crear, Leer, Actualizar, Borrar) estándar para la entidad
 * {@link Programa}. Extiende {@link JpaRepository}, y las operaciones de persistencia
 * subyacentes son manejadas por la implementación de Spring Data JPA.</p>
 *
 * <p>El repositorio opera sobre entidades {@link Ficha}, donde la clave primaria
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
 * @see Programa La clase de entidad gestionada por este repositorio, representando un programa o ficha de programa.
 * @see JpaRepository La interfaz base de Spring Data JPA que proporciona la funcionalidad CRUD.
 * @see Repository La anotación de Spring que marca esta interfaz como un componente de repositorio.
 */
@Repository
public interface FichaRepository extends JpaRepository<Ficha, Integer> {

    /**
     * Busca fichas por su código
     * @param codigo El código de la ficha a buscar
     * @return La ficha con el código especificado
     */
    Ficha findByCodigo(int codigo);

    /**
     * Verifica si existe una ficha con el código especificado
     * @param codigo El código a verificar
     * @return true si existe, false si no
     */
    boolean existsByCodigo(int codigo);

    /**
     * Busca fichas por el ID del programa asociado
     * @param programaId El ID del programa
     * @return Lista de fichas asociadas al programa
     */
    List<Ficha> findByProgramaId(int programaId);

    /**
     * Busca fichas que contengan el nombre especificado (búsqueda parcial case-insensitive)
     * @param nombre Parte del nombre a buscar
     * @return Lista de fichas que coinciden con el criterio
     */
    List<Ficha> findByNombreContainingIgnoreCase(String nombre);

    Optional<Ficha> findByCodigoAndProgramaId(int codigo, int programaId);

    /**
     * Busca una Ficha por su código y el programa al que pertenece.
     * Esto es útil para validar la unicidad del código de ficha dentro de un programa.
     *
     * @param codigo El código de la ficha a buscar.
     * @param programa El objeto Programa al que pertenece la ficha.
     * @return Un Optional que contiene la Ficha si se encuentra, o un Optional vacío si no.
     */
    Optional<Ficha> findByCodigoAndPrograma(Integer codigo, Programa programa);

    /**
     * Busca una Ficha por su código, el programa al que pertenece, y que no sea la ficha con el ID dado.
     * Esto es útil para validar la unicidad del código de ficha dentro de un programa
     * al actualizar una ficha existente (para permitir que la misma ficha mantenga su código).
     *
     * @param codigo El código de la ficha a buscar.
     * @param programa El objeto Programa al que pertenece la ficha.
     * @param id El ID de la ficha actual que se está actualizando (para excluirla de la búsqueda).
     * @return Un Optional que contiene la Ficha si se encuentra, o un Optional vacío si no.
     */
    Optional<Ficha> findByCodigoAndProgramaAndIdIsNot(Integer codigo, Programa programa, Integer id);

}