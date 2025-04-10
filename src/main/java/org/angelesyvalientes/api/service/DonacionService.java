package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Donacion;
import org.angelesyvalientes.api.persistence.repository.DonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Donacion}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar donaciones
 * de la base de datos a través del {@link DonacionRepository}.
 */
@Service
public class DonacionService {

    private final DonacionRepository donacionRepository;

    /**
     * Constructor de la clase {@code DonacionService}.
     * Recibe una instancia de {@link DonacionRepository} a través de la inyección de dependencias
     * para interactuar con la capa de persistencia.
     *
     * @param donacionRepository El repositorio para acceder a los datos de las donaciones.
     */
    @Autowired
    public DonacionService(DonacionRepository donacionRepository) {
        this.donacionRepository = donacionRepository;
    }

    /**
     * Obtiene una {@link Donacion} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que la donación no sea encontrada.
     *
     * @param id El identificador único de la donación a buscar.
     * @return Un {@link Optional} que contiene la {@link Donacion} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Donacion> getDonacion(Long id) {
        return donacionRepository.findById(id);
    }

    /**
     * Obtiene una lista con todas las {@link Donacion} almacenadas en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene todas las donaciones encontradas.
     * Si no hay donaciones, la lista estará vacía.
     */
    public List<Donacion> getAllDonaciones() {
        return donacionRepository.findAll();
    }

    /**
     * Guarda una nueva {@link Donacion} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param donacion El objeto {@link Donacion} a guardar.
     * @return El objeto {@link Donacion} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Donacion createDonacion(Donacion donacion) {
        return donacionRepository.save(donacion);
    }

    /**
     * Actualiza la información de una {@link Donacion} existente en la base de datos.
     * Primero, busca la donación por su ID. Si se encuentra, actualiza sus campos
     * con la información proporcionada en la {@code donacionActualizada} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si la donación no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id                  El identificador único de la donación a actualizar.
     * @param donacionActualizada El objeto {@link Donacion} con la información actualizada.
     * @return El objeto {@link Donacion} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra una donación con el ID proporcionado.
     */
    public Donacion updateDonacion(Long id, Donacion donacionActualizada) {
        Optional<Donacion> donacionExistente = donacionRepository.findById(id);

        if (donacionExistente.isPresent()) {
            Donacion donacion = donacionExistente.get();

            // Actualizar los campos
            donacion.setTipoDonacion(donacionActualizada.getTipoDonacion());
            donacion.setFecha(donacionActualizada.getFecha());
            donacion.setObservacion(donacionActualizada.getObservacion());

            return donacionRepository.save(donacion);
        } else {
            throw new RuntimeException("Donacion con ID " + id + " no encontrada.");
        }
    }

    /**
     * Elimina una {@link Donacion} de la base de datos por su identificador único.
     * Primero, verifica si la donación existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarla.
     * Si la donación no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único de la donación a eliminar.
     * @throws RuntimeException Si no se encuentra una donación con el ID proporcionado.
     */
    public void deleteDonacion(Long id) {
        Optional<Donacion> donacionExistente = donacionRepository.findById(id);

        if (donacionExistente.isPresent()) {
            donacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Donacion con ID " + id + " no encontrada.");
        }
    }
}