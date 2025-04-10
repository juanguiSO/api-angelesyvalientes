package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Genero;
import org.angelesyvalientes.api.persistence.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Genero}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar géneros
 * de la base de datos a través del {@link GeneroRepository}.
 */
@Service
public class GeneroService {

    /**
     * Repositorio para acceder a los datos de la entidad {@link Genero} en la base de datos.
     */
    @Autowired
    GeneroRepository generoRepository;

    /**
     * Obtiene un {@link Genero} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el género no sea encontrado.
     *
     * @param id El identificador único del género a buscar.
     * @return Un {@link Optional} que contiene el {@link Genero} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Genero> getGenero(Long id) {
        return generoRepository.findById(id);
    }

    /**
     * Obtiene una lista con todos los {@link Genero} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los géneros encontrados.
     * Si no hay géneros, la lista estará vacía.
     */
    public List<Genero> getGeneros() {
        return generoRepository.findAll();
    }

    /**
     * Guarda un nuevo {@link Genero} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param genero El objeto {@link Genero} a guardar.
     * @return El objeto {@link Genero} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Genero saveGenero(Genero genero) {
        return generoRepository.save(genero);
    }

    /**
     * Actualiza la información de un {@link Genero} existente en la base de datos.
     * Primero, busca el género por su ID. Si se encuentra, actualiza su nombre
     * con el valor proporcionado en el {@code generoActualizado} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si el género no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id              El identificador único del género a actualizar.
     * @param generoActualizado El objeto {@link Genero} con la información actualizada.
     * @return El objeto {@link Genero} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra un género con el ID proporcionado.
     */
    public Genero updateGenero(Long id, Genero generoActualizado) {
        Optional<Genero> generoExistente = generoRepository.findById(id);

        if (generoExistente.isPresent()) {
            Genero genero = generoExistente.get();

            // Actualizar los campos
            genero.setTxGenero(generoActualizado.getTxGenero());

            return generoRepository.save(genero);
        } else {
            throw new RuntimeException("Género con ID " + id + " no encontrado.");
        }
    }

    /**
     * Elimina un {@link Genero} de la base de datos por su identificador único.
     * Primero, verifica si el género existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarlo.
     * Si el género no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único del género a eliminar.
     * @throws RuntimeException Si no se encuentra un género con el ID proporcionado.
     */
    public void deleteGenero(Long id) {
        Optional<Genero> generoExistente = generoRepository.findById(id);

        if (generoExistente.isPresent()) {
            generoRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("Género con ID " + id + " no encontrado.");
        }
    }
}