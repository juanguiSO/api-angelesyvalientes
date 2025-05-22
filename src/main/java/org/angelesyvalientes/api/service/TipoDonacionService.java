package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.TipoDonacion;
import org.angelesyvalientes.api.persistence.repository.TipoDonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link TipoDonacion}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar tipos de donación
 * de la base de datos a través del {@link TipoDonacionRepository}.
 */
@Service
public class TipoDonacionService {

    private final TipoDonacionRepository tipoDonacionRepository;

    /**
     * Constructor de la clase {@code TipoDonacionService}.
     * Recibe una instancia de {@link TipoDonacionRepository} a través de la inyección de dependencias
     * para interactuar con la capa de persistencia.
     *
     * @param tipoDonacionRepository El repositorio para acceder a los datos de los tipos de donación.
     */
    @Autowired
    public TipoDonacionService(TipoDonacionRepository tipoDonacionRepository) {
        this.tipoDonacionRepository = tipoDonacionRepository;
    }

    /**
     * Obtiene un {@link TipoDonacion} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el tipo de donación no sea encontrado.
     *
     * @param id El identificador único del tipo de donación a buscar.
     * @return Un {@link Optional} que contiene el {@link TipoDonacion} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<TipoDonacion> getTipoDonacion(Integer id) {
        return tipoDonacionRepository.findById(id);
    }

    /**
     * Obtiene una lista con todos los {@link TipoDonacion} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los tipos de donación encontrados.
     * Si no hay tipos de donación, la lista estará vacía.
     */
    public List<TipoDonacion> getAllTipoDonaciones() {
        return tipoDonacionRepository.findAll();
    }

    /**
     * Guarda un nuevo {@link TipoDonacion} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param tipoDonacion El objeto {@link TipoDonacion} a guardar.
     * @return El objeto {@link TipoDonacion} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public TipoDonacion createTipoDonacion(TipoDonacion tipoDonacion) {
        return tipoDonacionRepository.save(tipoDonacion);
    }

    /**
     * Actualiza la información de un {@link TipoDonacion} existente en la base de datos.
     * Primero, busca el tipo de donación por su ID. Si se encuentra, actualiza su nombre
     * con el valor proporcionado en el {@code tipoDonacionActualizado} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si el tipo de donación no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id                    El identificador único del tipo de donación a actualizar.
     * @param tipoDonacionActualizado El objeto {@link TipoDonacion} con la información actualizada.
     * @return El objeto {@link TipoDonacion} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra un tipo de donación con el ID proporcionado.
     */
    public TipoDonacion updateTipoDonacion(Integer id, TipoDonacion tipoDonacionActualizado) {
        Optional<TipoDonacion> tipoDonacionExistente = tipoDonacionRepository.findById(id);

        if (tipoDonacionExistente.isPresent()) {
            TipoDonacion tipoDonacion = tipoDonacionExistente.get();

            // Actualizar los campos
            tipoDonacion.setTipoDonacion(tipoDonacionActualizado.getTipoDonacion());

            return tipoDonacionRepository.save(tipoDonacion);
        } else {
            throw new RuntimeException("TipoDonacion con ID " + id + " no encontrado.");
        }
    }

    /**
     * Elimina un {@link TipoDonacion} de la base de datos por su identificador único.
     * Primero, verifica si el tipo de donación existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarlo.
     * Si el tipo de donación no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único del tipo de donación a eliminar.
     * @throws RuntimeException Si no se encuentra un tipo de donación con el ID proporcionado.
     */
    public void deleteTipoDonacion(Integer id) {
        Optional<TipoDonacion> tipoDonacionExistente = tipoDonacionRepository.findById(id);

        if (tipoDonacionExistente.isPresent()) {
            tipoDonacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("TipoDonacion con ID " + id + " no encontrado.");
        }
    }
}