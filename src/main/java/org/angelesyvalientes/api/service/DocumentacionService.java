package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Documentacion;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.persistence.repository.DocumentacionRepository;
import org.angelesyvalientes.api.persistence.repository.PersonaRepository; // Necesitamos el PersonaRepository
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Documentacion}.
 */
@Service
public class DocumentacionService {

    private final DocumentacionRepository documentacionRepository;
    private final PersonaRepository personaRepository; // Inyectamos el PersonaRepository
    private static final Logger logger = LoggerFactory.getLogger(DocumentacionService.class);

    /**
     * Constructor de la clase {@code DocumentacionService}.
     * Recibe las instancias de los repositorios necesarios a través de la inyección de dependencias.
     *
     * @param documentacionRepository El repositorio para acceder a los datos de la documentación.
     * @param personaRepository       El repositorio para acceder a los datos de las personas.
     */
    @Autowired
    public DocumentacionService(DocumentacionRepository documentacionRepository, PersonaRepository personaRepository) {
        this.documentacionRepository = documentacionRepository;
        this.personaRepository = personaRepository;
    }

    public Optional<Documentacion> getDocumentacion(Long id) {
        return documentacionRepository.findById(id);
    }

    public List<Documentacion> getAllDocumentaciones() {
        return documentacionRepository.findAll();
    }

    /**
     * Guarda una nueva {@link Documentacion} en la base de datos,
     * verificando primero que la {@link Persona} asociada exista.
     *
     * @param documentacion El objeto {@link Documentacion} a guardar, que debe contener una {@link Persona} con un ID válido.
     * @return El objeto {@link Documentacion} guardado.
     * @throws IllegalArgumentException Si el objeto Persona es nulo o su ID es cero.
     * @throws RuntimeException         Si no se encuentra la Persona con el ID proporcionado.
     */
    @Transactional
    public Documentacion createDocumentacion(Documentacion documentacion) {
        logger.info("Iniciando createDocumentacion con la siguiente información: {}", documentacion);
        if (documentacion.getPersona() == null) {
            logger.warn("El objeto Persona dentro de Documentacion es nulo.");
            throw new IllegalArgumentException("El objeto Persona no puede ser nulo.");
        }
        if (documentacion.getPersona().getNmIdPersona() == 0) {
            logger.warn("El ID de la persona dentro de Documentacion es cero.");
            throw new IllegalArgumentException("El ID de la persona no puede ser cero.");
        }
        int personaId = documentacion.getPersona().getNmIdPersona();
        logger.info("Buscando Persona con ID: {}", personaId);
        Optional<Persona> personaExistente = personaRepository.findById(Long.valueOf(personaId));
        if (personaExistente.isPresent()) {
            Persona persona = personaExistente.get();
            logger.info("Persona encontrada: {}", persona);
            documentacion.setPersona(persona);
            Documentacion savedDocumentacion = documentacionRepository.save(documentacion);
            logger.info("Documentacion guardada con ID: {}", savedDocumentacion.getIdDocumentacion());
            return savedDocumentacion;
        } else {
            logger.warn("No se encontró la Persona con ID: {}", personaId);
            throw new RuntimeException("No se encontró la Persona con ID: " + personaId);
        }
    }

    public Documentacion updateDocumentacion(Long id, Documentacion documentacionActualizada) {
        Optional<Documentacion> documentacionExistente = documentacionRepository.findById(id);

        if (documentacionExistente.isPresent()) {
            Documentacion documentacion = documentacionExistente.get();
            documentacion.setPersona(documentacionActualizada.getPersona());
            documentacion.setTipoDocumentacion(documentacionActualizada.getTipoDocumentacion());
            documentacion.setUrlPdf(documentacionActualizada.getUrlPdf());
            documentacion.setFecha(documentacionActualizada.getFecha());
            return documentacionRepository.save(documentacion);
        } else {
            throw new RuntimeException("Documentacion con ID " + id + " no encontrada.");
        }
    }

    public void deleteDocumentacion(Long id) {
        Optional<Documentacion> documentacionExistente = documentacionRepository.findById(id);

        if (documentacionExistente.isPresent()) {
            documentacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Documentacion con ID " + id + " no encontrada.");
        }
    }
}