package org.angelesyvalientes.api.service;

import jakarta.transaction.Transactional;
import org.angelesyvalientes.api.persistence.entity.InformeClinico;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.persistence.repository.InformeClinicoRepository;
import org.angelesyvalientes.api.persistence.repository.PersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona las operaciones relacionadas con la entidad {@link InformeClinico}.
 * Proporciona métodos para obtener, crear, actualizar y eliminar informes clínicos
 * de la base de datos a través del {@link InformeClinicoRepository}.
 */
@Service
public class InformeClinicoService {

    private static final Logger logger = LoggerFactory.getLogger(InformeClinicoService.class);

    private final InformeClinicoRepository informeClinicoRepository;
    private final PersonaRepository personaRepository;

    @Autowired
    public InformeClinicoService(InformeClinicoRepository informeClinicoRepository, PersonaRepository personaRepository) {
        this.informeClinicoRepository = informeClinicoRepository;
        this.personaRepository = personaRepository;
        logger.info("InformeClinicoService inicializado.");
    }

    public Optional<InformeClinico> getInformeClinico(Long id) {
        logger.info("Obteniendo InformeClinico con ID: {}", id);
        return informeClinicoRepository.findById(id);
    }

    public List<InformeClinico> getAllInformesClinicos() {
        logger.info("Obteniendo todos los InformesClinicos.");
        List<InformeClinico> informes = informeClinicoRepository.findAll();
        logger.info("Número de informes clínicos encontrados: {}", informes.size());
        return informes;
    }

    @Transactional
    public InformeClinico createInformeClinico(InformeClinico informeClinico) {
        logger.info("Iniciando la creación de InformeClinico con datos: {}", informeClinico);

        if (informeClinico.getPersona() == null || informeClinico.getPersona().getNmIdPersona() == 0) {
            logger.warn("El objeto Persona o su ID dentro de InformeClinico es nulo o inválido.");
            throw new IllegalArgumentException("La Persona asociada al informe clínico es obligatoria.");
        }

        Long personaId = Long.valueOf(informeClinico.getPersona().getNmIdPersona());
        logger.info("Buscando Persona con ID: {}", personaId);

        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> {
                    logger.warn("No se encontró la Persona con ID: {}", personaId);
                    return new RuntimeException("No se encontró la Persona con ID: " + personaId + " para asociar al informe clínico.");
                });

        informeClinico.setPersona(persona);
        InformeClinico savedInforme = informeClinicoRepository.save(informeClinico);
        logger.info("InformeClinico creado exitosamente con ID: {}", savedInforme.getIdInformeClinico());

        return savedInforme;
    }

    public InformeClinico updateInformeClinico(Long id, InformeClinico informeClinicoActualizado) {
        logger.info("Actualizando InformeClinico con ID: {}, con los siguientes datos: {}", id, informeClinicoActualizado);
        Optional<InformeClinico> informeClinicoExistente = informeClinicoRepository.findById(id);

        if (informeClinicoExistente.isPresent()) {
            InformeClinico informeClinico = informeClinicoExistente.get();

            if (informeClinicoActualizado.getPersona() == null || informeClinicoActualizado.getPersona().getNmIdPersona() == 0) {
                logger.warn("El objeto Persona o su ID dentro de informeClinicoActualizado es nulo.");
                throw new IllegalArgumentException("La Persona asociada al informe clínico es obligatoria para la actualización.");
            }

            Long personaId = Long.valueOf(informeClinicoActualizado.getPersona().getNmIdPersona());
            logger.info("Buscando Persona con ID: {}", personaId);
            Persona personaExistente = personaRepository.findById(personaId)
                    .orElseThrow(() -> {
                        logger.warn("No se encontró la Persona con ID: {}", personaId);
                        return new RuntimeException("No se encontró la Persona con ID: " + personaId + " para actualizar el informe clínico.");
                    });
            informeClinico.setPersona(personaExistente);
            informeClinico.setFecha(informeClinicoActualizado.getFecha());
            informeClinico.setTipoInforme(informeClinicoActualizado.getTipoInforme());
            informeClinico.setProfesional(informeClinicoActualizado.getProfesional());
            informeClinico.setUrlPdf(informeClinicoActualizado.getUrlPdf());

            InformeClinico updatedInforme = informeClinicoRepository.save(informeClinico);
            logger.info("InformeClinico con ID: {} actualizado exitosamente.", updatedInforme.getIdInformeClinico());
            return updatedInforme;
        } else {
            logger.warn("No se encontró InformeClinico con ID: {}", id);
            throw new RuntimeException("InformeClinico con ID " + id + " no encontrado.");
        }
    }

    public void deleteInformeClinico(Long id) {
        logger.info("Eliminando InformeClinico con ID: {}", id);
        Optional<InformeClinico> informeClinicoExistente = informeClinicoRepository.findById(id);

        if (informeClinicoExistente.isPresent()) {
            informeClinicoRepository.deleteById(id);
            logger.info("InformeClinico con ID: {} eliminado exitosamente.", id);
        } else {
            logger.warn("No se encontró InformeClinico con ID: {} para eliminar.", id);
            throw new RuntimeException("InformeClinico con ID " + id + " no encontrado.");
        }
    }

    /**
     * Guarda la URL del PDF del informe clínico asociado a un informe clínico específico.
     * Verifica que la persona asociada al informe clínico sea la correcta.
     *
     * @param personaId       El ID de la persona asociada al informe clínico.
     * @param urlPdf          La URL del archivo PDF en Google Drive (el fileId).
     * @param idInformeClinico El ID del informe clínico al que se asociará la URL.
     */
    @Transactional
    public void guardarUrlPdf(Long personaId, String urlPdf, Long idInformeClinico) {
        logger.info("Guardando URL del PDF: {} para el InformeClinico con ID: {}", urlPdf, idInformeClinico);

        // 1. Verificar que la persona exista
        Optional<Persona> personaOptional = personaRepository.findById(personaId);
        if (personaOptional.isEmpty()) {
            logger.warn("No se encontró la Persona con ID: {} al intentar guardar la URL del PDF.", personaId);
            throw new RuntimeException("No se encontró la Persona con ID: " + personaId + ".");
        }
        Persona persona = personaOptional.get();

        // 2. Buscar el InformeClinico por su ID
        Optional<InformeClinico> informeClinicoOptional = informeClinicoRepository.findById(idInformeClinico);
        if (informeClinicoOptional.isEmpty()) {
            logger.warn("No se encontró el InformeClinico con ID: {}", idInformeClinico);
            throw new RuntimeException("No se encontró el InformeClinico con ID: " + idInformeClinico + ".");
        }
        InformeClinico informeClinico = informeClinicoOptional.get();

        // 3. Verificar que la persona asociada al informe clínico sea la correcta
        if (!informeClinico.getPersona().equals(persona)) {
            logger.warn("La Persona con ID: {} no está asociada al InformeClinico con ID: {}", personaId, idInformeClinico);
            throw new IllegalArgumentException("La Persona con ID: " + personaId + " no está asociada a este informe clínico.");
        }

        // 4. Actualizar la URL del PDF y guardar
        informeClinico.setUrlPdf(urlPdf);
        informeClinicoRepository.save(informeClinico);
        logger.info("URL del PDF actualizada para el InformeClinico con ID: {}", idInformeClinico);
    }
}