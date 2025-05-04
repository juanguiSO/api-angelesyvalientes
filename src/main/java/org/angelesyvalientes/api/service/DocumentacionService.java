package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Documentacion;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.persistence.repository.DocumentacionRepository;
import org.angelesyvalientes.api.persistence.repository.PersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Documentacion}.
 */
@Service
public class DocumentacionService {

    private final DocumentacionRepository documentacionRepository;
    private final PersonaRepository personaRepository;
    private static final Logger logger = LoggerFactory.getLogger(DocumentacionService.class);

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
     * Guarda una nueva {@link Documentacion} en la base de datos, validando y asociando la {@link Persona}.
     * La URL del PDF se establecerá posteriormente.
     * @param documentacion El objeto {@link Documentacion} a guardar con la información de la persona y el tipo de documento.
     * @return El objeto {@link Documentacion} guardado.
     * @throws IllegalArgumentException Si el objeto Persona es nulo o su ID es cero, o si el tipo de documento es nulo o vacío.
     * @throws RuntimeException Si no se encuentra la Persona asociada al ID proporcionado.
     */
    @Transactional
    public Documentacion createDocumentacion(Documentacion documentacion) {
        logger.info("Iniciando createDocumentacion con la siguiente información (sin URL): {}", documentacion);
        if (documentacion.getPersona() == null) {
            logger.warn("El objeto Persona dentro de Documentacion es nulo.");
            throw new IllegalArgumentException("El objeto Persona no puede ser nulo.");
        }
        if (documentacion.getPersona().getNmIdPersona() == 0) {
            logger.warn("El ID de la persona dentro de Documentacion es cero.");
            throw new IllegalArgumentException("El ID de la persona no puede ser cero.");
        }
        if (documentacion.getTipoDocumentacion() == null || documentacion.getTipoDocumentacion().trim().isEmpty()) {
            logger.warn("El tipo de documento dentro de Documentacion no puede ser nulo o vacío.");
            throw new IllegalArgumentException("El tipo de documento no puede ser nulo o vacío.");
        }
        Long personaId = Long.valueOf(documentacion.getPersona().getNmIdPersona());
        logger.info("Buscando Persona con ID: {}", personaId);
        Optional<Persona> personaExistente = personaRepository.findById(personaId);
        if (personaExistente.isPresent()) {
            Persona persona = personaExistente.get();
            logger.info("Persona encontrada: {}", persona);
            documentacion.setPersona(persona);
            // Establecer la URL del PDF a null al momento de la creación
            documentacion.setUrlPdf(null);
            Documentacion savedDocumentacion = documentacionRepository.save(documentacion);
            logger.info("Documentacion guardada con ID: {}", savedDocumentacion.getIdDocumentacion());
            return savedDocumentacion;
        } else {
            logger.warn("No se encontró la Persona con ID: {}", personaId);
            throw new RuntimeException("No se encontró la Persona con ID: " + personaId);
        }
    }

    public boolean existePersona(Long idPersona) {
        return personaRepository.existsById(idPersona);
    }

    /**
     * Guarda el ID del archivo PDF en la tabla documentacion asociado a una persona y tipo de documento.
     * Si ya existe una documentación para esa persona con el mismo tipo, actualiza la URL del PDF.
     * Si no existe, crea una nueva entrada en Documentacion.
     * @param idPersona El ID de la persona.
     * @param pdfFileId El ID del archivo PDF en Google Drive.
     * @param tipoDocumentacion Tipo de documento que se está guardando.
     * @return La documentación actualizada o creada.
     * @throws RuntimeException Si no se encuentra la persona con el ID proporcionado.
     */
    @Transactional
    public Documentacion guardarIdDocumento(Long idPersona, String pdfFileId, String tipoDocumentacion) {
        logger.info("Actualizando o creando Documentación para la Persona con ID: {} y tipo: {}", idPersona, tipoDocumentacion);

        Optional<Persona> personaExistente = personaRepository.findById(idPersona);
        if (!personaExistente.isPresent()) {
            logger.warn("No se encontró la Persona con ID: {}", idPersona);
            throw new RuntimeException("No se encontró la Persona con ID: " + idPersona);
        }
        Persona persona = personaExistente.get();

        Optional<Documentacion> documentacionExistente = documentacionRepository.findByPersonaAndTipoDocumentacion(persona, tipoDocumentacion);

        Documentacion documentacion;
        if (documentacionExistente.isPresent()) {
            documentacion = documentacionExistente.get();
            logger.info("Documentación existente encontrada con ID {} para la Persona con ID: {} y tipo: {}", documentacion.getIdDocumentacion(), idPersona, tipoDocumentacion);
            documentacion.setUrlPdf(pdfFileId); // Actualizar la URL del PDF
        } else {
            documentacion = new Documentacion();
            documentacion.setPersona(persona);
            documentacion.setTipoDocumentacion(tipoDocumentacion);
            documentacion.setUrlPdf(pdfFileId);
            documentacion.setFecha(LocalDate.now());
            logger.info("No existía documentación para la Persona con ID: {} y tipo: {}, creando nueva entrada.", idPersona, tipoDocumentacion);
        }

        Documentacion updatedDocumentacion = documentacionRepository.save(documentacion);
        logger.info("PDF guardado en Documentación con ID: {}", updatedDocumentacion.getIdDocumentacion());

        return updatedDocumentacion;
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

    /**
     * Obtiene todos los documentos asociados a una persona específica.
     *
     * @param personaId El ID de la persona.
     * @return Una lista de {@link Documentacion} asociadas a la persona.
     */
    public List<Documentacion> getDocumentacionesPorPersona(Integer personaId) {
        logger.info("Obteniendo documentos para la Persona con ID: {}", personaId);
        return documentacionRepository.findByPersona_NmIdPersona(personaId);
    }




}