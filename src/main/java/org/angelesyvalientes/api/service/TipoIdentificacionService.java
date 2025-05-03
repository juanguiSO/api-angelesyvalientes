package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.TipoIdentificacion;
import org.angelesyvalientes.api.persistence.repository.TipoIdentificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link TipoIdentificacion}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar tipos de identificación
 * de la base de datos a través del {@link TipoIdentificacionRepository}.
 */
@Service
public class TipoIdentificacionService {

    /**
     * Repositorio para acceder a los datos de la entidad {@link TipoIdentificacion} en la base de datos.
     */
    @Autowired
    TipoIdentificacionRepository tipoIdentificacionRepository;

    /**
     * Obtiene un {@link TipoIdentificacion} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que el tipo de identificación no sea encontrado.
     *
     * @param id El identificador único del tipo de identificación a buscar.
     * @return Un {@link Optional} que contiene el {@link TipoIdentificacion} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<TipoIdentificacion> getTipoIdentificacion(int id) {
        return tipoIdentificacionRepository.findById(id);
    }

    /**
     * Obtiene una lista con todos los {@link TipoIdentificacion} almacenados en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todos los tipos de identificación encontrados.
     * Si no hay tipos de identificación, la lista estará vacía.
     */
    public List<TipoIdentificacion> getTiposIdentificacion() {
        return tipoIdentificacionRepository.findAll();
    }

    /**
     * Guarda un nuevo {@link TipoIdentificacion} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param tipoIdentificacion El objeto {@link TipoIdentificacion} a guardar.
     * @return El objeto {@link TipoIdentificacion} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public TipoIdentificacion saveTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        return tipoIdentificacionRepository.save(tipoIdentificacion);
    }

    /**
     * Actualiza la información de un {@link TipoIdentificacion} existente en la base de datos.
     * Primero, busca el tipo de identificación por su ID. Si se encuentra, actualiza su nombre
     * y estado con los valores proporcionados en el {@code tipoIdentificacionActualizado}
     * y luego guarda los cambios utilizando el método {@code save} del repositorio.
     * Si el tipo de identificación no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id                       El identificador único del tipo de identificación a actualizar.
     * @param tipoIdentificacionActualizado El objeto {@link TipoIdentificacion} con la información actualizada.
     * @return El objeto {@link TipoIdentificacion} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra un tipo de identificación con el ID proporcionado.
     */
    public TipoIdentificacion updateTipoIdentificacion(int id, TipoIdentificacion tipoIdentificacionActualizado) {
        Optional<TipoIdentificacion> tipoExistente = tipoIdentificacionRepository.findById(id);

        if (tipoExistente.isPresent()) {
            TipoIdentificacion tipoIdentificacion = tipoExistente.get();

            // Actualizar los campos
            tipoIdentificacion.setTxTipoIdentificacion(tipoIdentificacionActualizado.getTxTipoIdentificacion());
            tipoIdentificacion.setBoEstado(tipoIdentificacionActualizado.getBoEstado());

            return tipoIdentificacionRepository.save(tipoIdentificacion);
        } else {
            throw new RuntimeException("TipoIdentificacion con ID " + id + " no encontrado.");
        }
    }

    /**
     * Elimina un {@link TipoIdentificacion} de la base de datos por su identificador único.
     * Primero, verifica si el tipo de identificación existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarlo.
     * Si el tipo de identificación no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único del tipo de identificación a eliminar.
     * @throws RuntimeException Si no se encuentra un tipo de identificación con el ID proporcionado.
     */
    public void deleteTipoIdentificacion(int id) {
        Optional<TipoIdentificacion> tipoExistente = tipoIdentificacionRepository.findById(id);

        if (tipoExistente.isPresent()) {
            tipoIdentificacionRepository.deleteById(id); // Elimina el registro de la base de datos
        } else {
            throw new RuntimeException("TipoIdentificacion con ID " + id + " no encontrado.");
        }
    }
}