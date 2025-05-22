package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Angel;
import org.angelesyvalientes.api.persistence.repository.AngelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Angel}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar ángeles
 * de la base de datos a través del {@link AngelRepository}.
 */
@Service
public class AngelService {

    private final AngelRepository angelRepository;

    /**
     * Constructor de la clase {@code AngelService}.
     * Recibe una instancia de {@link AngelRepository} a través de la inyección de dependencias
     * para interactuar con la capa de persistencia.
     *
     * @param angelRepository El repositorio para acceder a los datos de los ángeles.
     */
    @Autowired
    public AngelService(AngelRepository angelRepository) {
        this.angelRepository = angelRepository;
    }

    /**
     * Obtiene una lista con todos los {@link Angel} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los ángeles encontrados.
     * Si no hay ángeles, la lista estará vacía.
     */
    public List<Angel> findAll() {
        return angelRepository.findAll();
    }

    /**
     * Obtiene un {@link Angel} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el ángel no sea encontrado.
     *
     * @param id El identificador único del ángel a buscar.
     * @return Un {@link Optional} que contiene el {@link Angel} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Angel findById(Long id) {
        Optional<Angel> optional = angelRepository.findById(id);
        return optional.orElse(null);
    }

    /**
     * Guarda un nuevo {@link Angel} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param angel El objeto {@link Angel} a guardar.
     * @return El objeto {@link Angel} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Angel save(Angel angel) {
        return angelRepository.save(angel);
    }

    /**
     * Elimina un {@link Angel} de la base de datos por su identificador único.
     * Utiliza el método {@code deleteById} del repositorio.
     *
     * @param id El identificador único del ángel a eliminar.
     */
    public void deleteById(Long id) {
        angelRepository.deleteById(id);
    }

    /**
     * Obtiene el número total de Angeles registrados.
     * @return El conteo total de Angeles.
     */
    public long countAllAngeles() {
        return angelRepository.count();
    }


}