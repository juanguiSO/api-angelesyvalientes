package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Educacion;
import org.angelesyvalientes.api.persistence.repository.EducacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Educacion}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar información educativa
 * de la base de datos a través del {@link EducacionRepository}.
 */
@Service
public class EducacionService {

    private final EducacionRepository educacionRepository;

    /**
     * Constructor de la clase {@code EducacionService}.
     * Recibe una instancia de {@link EducacionRepository} a través de la inyección de dependencias
     * para interactuar con la capa de persistencia.
     *
     * @param educacionRepository El repositorio para acceder a los datos de educación.
     */
    @Autowired
    public EducacionService(EducacionRepository educacionRepository) {
        this.educacionRepository = educacionRepository;
    }

    /**
     * Obtiene una {@link Educacion} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que la información educativa no sea encontrada.
     *
     * @param id El identificador único de la información educativa a buscar.
     * @return Un {@link Optional} que contiene la {@link Educacion} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Educacion> getEducacion(Long id) {
        return educacionRepository.findById(id);
    }

    /**
     * Obtiene una lista con toda la información de {@link Educacion} almacenada en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene toda la información educativa encontrada.
     * Si no hay información educativa, la lista estará vacía.
     */
    public List<Educacion> getAllEducaciones() {
        return educacionRepository.findAll();
    }

    /**
     * Guarda una nueva {@link Educacion} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param educacion El objeto {@link Educacion} a guardar.
     * @return El objeto {@link Educacion} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Educacion createEducacion(Educacion educacion) {
        return educacionRepository.save(educacion);
    }

    /**
     * Actualiza la información de {@link Educacion} existente en la base de datos.
     * Primero, busca la información educativa por su ID. Si se encuentra, actualiza sus campos
     * con la información proporcionada en el {@code educacionActualizada} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si la información educativa no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id                  El identificador único de la información educativa a actualizar.
     * @param educacionActualizada El objeto {@link Educacion} con la información actualizada.
     * @return El objeto {@link Educacion} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra información educativa con el ID proporcionado.
     */
    public Educacion updateEducacion(Long id, Educacion educacionActualizada) {
        Optional<Educacion> educacionExistente = educacionRepository.findById(id);

        if (educacionExistente.isPresent()) {
            Educacion educacion = educacionExistente.get();

            // Actualizar los campos
            educacion.setPersona(educacionActualizada.getPersona());
            educacion.setInstitucion(educacionActualizada.getInstitucion());
            educacion.setNivel(educacionActualizada.getNivel());

            return educacionRepository.save(educacion);
        } else {
            throw new RuntimeException("Educacion con ID " + id + " no encontrada.");
        }
    }

    /**
     * Elimina información de {@link Educacion} de la base de datos por su identificador único.
     * Primero, verifica si la información educativa existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarla.
     * Si la información educativa no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único de la información educativa a eliminar.
     * @throws RuntimeException Si no se encuentra información educativa con el ID proporcionado.
     */
    public void deleteEducacion(Long id) {
        Optional<Educacion> educacionExistente = educacionRepository.findById(id);

        if (educacionExistente.isPresent()) {
            educacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Educacion con ID " + id + " no encontrada.");
        }
    }
}