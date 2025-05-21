package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Programa;
import org.angelesyvalientes.api.persistence.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Programa}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar programas
 * de la base de datos a través del {@link ProgramaRepository}.
 */
@Service
public class ProgramaService {

    /**
     * Repositorio para acceder a los datos de la entidad {@link Programa} en la base de datos.
     */
    @Autowired
    ProgramaRepository programaRepository;

    /**
     * Obtiene un {@link Programa} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el programa no sea encontrado.
     *
     * @param id El identificador único del programa a buscar.
     * @return Un {@link Optional} que contiene el {@link Programa} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Programa> getPrograma(Integer id) {
        return programaRepository.findById(id);
    }

    /**
     * Obtiene una lista con todos los {@link Programa} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los programas encontrados.
     * Si no hay programas, la lista estará vacía.
     */
    public List<Programa> getProgramas() {
        return programaRepository.findAll();
    }

    /**
     * Guarda un nuevo {@link Programa} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param programa El objeto {@link Programa} a guardar.
     * @return El objeto {@link Programa} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Programa savePrograma(Programa programa) {
        return programaRepository.save(programa);
    }

    /**
     * Actualiza la información de un {@link Programa} existente en la base de datos.
     * Primero, busca el programa por su ID. Si se encuentra, actualiza sus campos
     * y luego guarda los cambios utilizando el método {@code save} del repositorio.
     * Si el programa no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único del programa a actualizar.
     * @param programaActualizado El objeto {@link Programa} con la información actualizada.
     * @return El objeto {@link Programa} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra un programa con el ID proporcionado.
     */
    public Programa updatePrograma(Integer id, Programa programaActualizado) {
        Optional<Programa> programaExistente = programaRepository.findById(id);

        if (programaExistente.isPresent()) {
            Programa programa = programaExistente.get();

            programa.setNombre(programaActualizado.getNombre());
            programa.setEstado(programaActualizado.isEstado());
            programa.setFecha(programaActualizado.getFecha());
            return programaRepository.save(programa);
        } else {
            throw new RuntimeException("Programa con ID " + id + " no encontrado.");
        }
    }


    /**
     * Elimina un {@link Programa} de la base de datos por su identificador único.
     * Primero, verifica si el programa existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarlo.
     * Si el programa no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único del programa a eliminar.
     * @throws RuntimeException Si no se encuentra un programa con el ID proporcionado.
     */
    public void deletePrograma(Integer id) {
        Optional<Programa> programaExistente = programaRepository.findById(id);

        if (programaExistente.isPresent()) {
            programaRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("Programa con ID " + id + " no encontrado.");
        }
    }
}