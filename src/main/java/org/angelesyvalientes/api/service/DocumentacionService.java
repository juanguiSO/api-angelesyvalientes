package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Documentacion;
import org.angelesyvalientes.api.persistence.repository.DocumentacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Documentacion}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar documentación
 * de la base de datos a través del {@link DocumentacionRepository}.
 */
@Service
public class DocumentacionService {

    private final DocumentacionRepository documentacionRepository;

    /**
     * Constructor de la clase {@code DocumentacionService}.
     * Recibe una instancia de {@link DocumentacionRepository} a través de la inyección de dependencias
     * para interactuar con la capa de persistencia.
     *
     * @param documentacionRepository El repositorio para acceder a los datos de la documentación.
     */
    @Autowired
    public DocumentacionService(DocumentacionRepository documentacionRepository) {
        this.documentacionRepository = documentacionRepository;
    }

    /**
     * Obtiene un {@link Documentacion} de la base de datos por su identificador único.
     * Utiliza el método {@code findById} del repositorio, que devuelve un {@link Optional}
     * para manejar el caso en que la documentación no sea encontrada.
     *
     * @param id El identificador único de la documentación a buscar.
     * @return Un {@link Optional} que contiene la {@link Documentacion} si se encuentra,
     * o un {@link Optional} vacío en caso contrario.
     */
    public Optional<Documentacion> getDocumentacion(Long id) {
        return documentacionRepository.findById(id);
    }

    /**
     * Obtiene una lista con toda la {@link Documentacion} almacenada en la base de datos.
     * Utiliza el método {@code findAll} del repositorio.
     *
     * @return Una {@link List} que contiene toda la documentación encontrada.
     * Si no hay documentación, la lista estará vacía.
     */
    public List<Documentacion> getAllDocumentaciones() {
        return documentacionRepository.findAll();
    }

    /**
     * Guarda una nueva {@link Documentacion} en la base de datos.
     * Utiliza el método {@code save} del repositorio.
     *
     * @param documentacion El objeto {@link Documentacion} a guardar.
     * @return El objeto {@link Documentacion} guardado, que puede incluir
     * identificadores generados por la base de datos.
     */
    public Documentacion createDocumentacion(Documentacion documentacion) {
        return documentacionRepository.save(documentacion);
    }

    /**
     * Actualiza la información de una {@link Documentacion} existente en la base de datos.
     * Primero, busca la documentación por su ID. Si se encuentra, actualiza sus campos
     * con la información proporcionada en el {@code documentacionActualizada} y luego
     * guarda los cambios utilizando el método {@code save} del repositorio.
     * Si la documentación no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id                      El identificador único de la documentación a actualizar.
     * @param documentacionActualizada El objeto {@link Documentacion} con la información actualizada.
     * @return El objeto {@link Documentacion} actualizado y guardado en la base de datos.
     * @throws RuntimeException Si no se encuentra documentación con el ID proporcionado.
     */
    public Documentacion updateDocumentacion(Long id, Documentacion documentacionActualizada) {
        Optional<Documentacion> documentacionExistente = documentacionRepository.findById(id);

        if (documentacionExistente.isPresent()) {
            Documentacion documentacion = documentacionExistente.get();

            // Actualizar los campos
            documentacion.setPersona(documentacionActualizada.getPersona());
            documentacion.setTipoDocumentacion(documentacionActualizada.getTipoDocumentacion());
            documentacion.setUrlPdf(documentacionActualizada.getUrlPdf());
            documentacion.setFecha(documentacionActualizada.getFecha());

            return documentacionRepository.save(documentacion);
        } else {
            throw new RuntimeException("Documentacion con ID " + id + " no encontrada.");
        }
    }

    /**
     * Elimina una {@link Documentacion} de la base de datos por su identificador único.
     * Primero, verifica si la documentación existe. Si existe, utiliza el método
     * {@code deleteById} del repositorio para eliminarla.
     * Si la documentación no se encuentra, lanza una {@link RuntimeException}.
     *
     * @param id El identificador único de la documentación a eliminar.
     * @throws RuntimeException Si no se encuentra documentación con el ID proporcionado.
     */
    public void deleteDocumentacion(Long id) {
        Optional<Documentacion> documentacionExistente = documentacionRepository.findById(id);

        if (documentacionExistente.isPresent()) {
            documentacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Documentacion con ID " + id + " no encontrada.");
        }
    }
}