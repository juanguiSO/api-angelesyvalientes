package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Educacion;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.persistence.repository.EducacionRepository;
import org.angelesyvalientes.api.persistence.repository.PersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link Educacion}.
 */
@Service
public class EducacionService {
    private static final Logger logger = LoggerFactory.getLogger(EducacionService.class);
    private final EducacionRepository educacionRepository;
    private final PersonaRepository personaRepository;

    @Autowired
    public EducacionService(EducacionRepository educacionRepository, PersonaRepository personaRepository) {
        this.educacionRepository = educacionRepository;
        this.personaRepository = personaRepository;
    }

    public Optional<Educacion> getEducacion(Long id) {
        return educacionRepository.findById(id);
    }

    public List<Educacion> getAllEducaciones() {
        return educacionRepository.findAll();
    }

    @Transactional
    public Educacion createEducacion(Educacion educacion) {
        logger.info("Iniciando createEducacion con la siguiente información: {}", educacion);
        if (educacion.getPersona() == null) {
            logger.warn("El objeto Persona dentro de Educacion es nulo.");
            throw new IllegalArgumentException("El objeto Persona no puede ser nulo.");
        }
        if (educacion.getPersona().getNmIdPersona() == 0) {
            logger.warn("El ID de la persona dentro de Educacion es cero.");
            throw new IllegalArgumentException("El ID de la persona no puede ser cero.");
        }
        Long personaId = Long.valueOf(educacion.getPersona().getNmIdPersona());
        logger.info("Buscando Persona con ID: {}", personaId);
        Optional<Persona> personaExistente = personaRepository.findById(personaId);
        if (personaExistente.isPresent()) {
            Persona persona = personaExistente.get();
            logger.info("Persona encontrada: {}", persona);
            educacion.setPersona(persona);
            Educacion savedEducacion = educacionRepository.save(educacion);
            logger.info("Educacion guardada con ID: {}", savedEducacion.getIdEducacion());
            return savedEducacion;
        } else {
            logger.warn("No se encontró la Persona con ID: {}", personaId);
            throw new RuntimeException("No se encontró la Persona con ID: " + personaId);
        }
    }

    @Transactional
    public Educacion updateEducacion(Long id, Educacion educacionActualizada) {
        logger.info("Iniciando updateEducacion con ID: {} y la siguiente información: {}", id, educacionActualizada);

        Optional<Educacion> educacionExistente = educacionRepository.findById(id);

        if (educacionExistente.isPresent()) {
            Educacion educacion = educacionExistente.get();
            logger.info("Educacion existente encontrada: {}", educacion);

            if (educacionActualizada.getPersona() == null) {
                logger.warn("El objeto Persona dentro de educacionActualizada es nulo.");
                throw new IllegalArgumentException("El objeto Persona no puede ser nulo para la actualización.");
            }

            if (educacionActualizada.getPersona().getNmIdPersona() == 0) {
                logger.warn("El ID de la persona dentro de educacionActualizada es cero.");
                throw new IllegalArgumentException("El ID de la persona no puede ser cero para la actualización.");
            }

            Long personaId = Long.valueOf(educacionActualizada.getPersona().getNmIdPersona());
            logger.info("Buscando Persona con ID: {}", personaId);
            Optional<Persona> personaExistente = personaRepository.findById(personaId);

            if (personaExistente.isPresent()) {
                Persona persona = personaExistente.get();
                logger.info("Persona encontrada: {}", persona);
                educacion.setPersona(persona);
                educacion.setInstitucion(educacionActualizada.getInstitucion());
                educacion.setNivel(educacionActualizada.getNivel());
                Educacion updatedEducacion = educacionRepository.save(educacion);
                logger.info("Educacion con ID: {} actualizada: {}", updatedEducacion.getIdEducacion(), updatedEducacion);
                return updatedEducacion;
            } else {
                logger.warn("No se encontró la Persona con ID: {}", personaId);
                throw new RuntimeException("No se encontró la Persona con ID: " + personaId);
            }
        } else {
            logger.warn("No se encontró la Educacion con ID: {}", id);
            throw new RuntimeException("Educacion con ID " + id + " no encontrada.");
        }
    }

    public void deleteEducacion(Long id) {
        logger.info("Iniciando deleteEducacion con ID: {}", id);
        Optional<Educacion> educacionExistente = educacionRepository.findById(id);
        if (educacionExistente.isPresent()) {
            logger.info("Educacion con ID: {} encontrada, procediendo a eliminar.", id);
            educacionRepository.deleteById(id);
            logger.info("Educacion con ID: {} eliminada.", id);
        } else {
            logger.warn("No se encontró la Educacion con ID: {} para eliminar.", id);
            throw new RuntimeException("Educacion con ID " + id + " no encontrada.");
        }
    }
}