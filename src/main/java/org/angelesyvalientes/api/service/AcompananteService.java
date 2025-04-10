package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Acompanante;
import org.angelesyvalientes.api.persistence.repository.AcompananteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Acompanante}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar acompañantes
 * de la base de datos a través del {@link AcompananteRepository}.
 */
@Service
public class AcompananteService {

    private final AcompananteRepository acompananteRepository;

    /**
     * Constructor de la clase {@code AcompananteService}.
     * Recibe una instancia de {@link AcompananteRepository} a través de la inyección de dependencias
     * para interactuar con la capa de persistencia.
     *
     * @param acompananteRepository El repositorio para acceder a los datos de los acompañantes.
     */
    @Autowired
    public AcompananteService(AcompananteRepository acompananteRepository) {
        this.acompananteRepository = acompananteRepository;
    }

    /**
     * Obtiene un {@link Acompanante} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el acompañante no sea encontrado.
     *
     * @param id El identificador único del acompañante a buscar.
     * @return Un {@link Optional} que contiene el {@link Acompanante} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Acompanante> getAcompanante(Long id) {
        return acompananteRepository.findById(id);
    }

    /**
     * Obtiene una lista con todos los {@link Acompanante} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los acompañantes encontrados.
     * Si no hay acompañantes, la lista estará vacía.
     */
    public List<Acompanante> getAllAcompanantes() {
        return acompananteRepository.findAll();
    }

    /**
     * Guarda un nuevo {@link Acompanante} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param acompanante El objeto {@link Acompanante} a guardar.
     * @return El objeto {@link Acompanante} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Acompanante createAcompanante(Acompanante acompanante) {
        return acompananteRepository.save(acompanante);
    }

    /**
     * Actualiza la información de un {@link Acompanante} existente en la base de datos.
     * Primero, busca el acompañante por su ID. Si se encuentra, actualiza sus campos
     * con la información proporcionada en el {@code acompananteActualizado} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si el acompañante no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id                  El identificador único del acompañante a actualizar.
     * @param acompananteActualizado El objeto {@link Acompanante} con la información actualizada.
     * @return El objeto {@link Acompanante} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra un acompañante con el ID proporcionado.
     */
    public Acompanante updateAcompanante(Long id, Acompanante acompananteActualizado) {
        Optional<Acompanante> acompananteExistente = acompananteRepository.findById(id);

        if (acompananteExistente.isPresent()) {
            Acompanante acompanante = acompananteExistente.get();

            // Actualizar los campos
            acompanante.setPersona(acompananteActualizado.getPersona());
            acompanante.setNombreAcompanante(acompananteActualizado.getNombreAcompanante());
            acompanante.setTelefonoAcompanante(acompananteActualizado.getTelefonoAcompanante());

            return acompananteRepository.save(acompanante);
        } else {
            throw new RuntimeException("Acompanante con ID " + id + " no encontrado.");
        }
    }

    /**
     * Elimina un {@link Acompanante} de la base de datos por su identificador único.
     * Primero, verifica si el acompañante existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarlo.
     * Si el acompañante no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único del acompañante a eliminar.
     * @throws RuntimeException Si no se encuentra un acompañante con el ID proporcionado.
     */
    public void deleteAcompanante(Long id) {
        Optional<Acompanante> acompananteExistente = acompananteRepository.findById(id);

        if (acompananteExistente.isPresent()) {
            acompananteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Acompanante con ID " + id + " no encontrado.");
        }
    }
}