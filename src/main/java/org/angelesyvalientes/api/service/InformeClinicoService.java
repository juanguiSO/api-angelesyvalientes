package org.angelesyvalientes.api.service;

import jakarta.transaction.Transactional;
import org.angelesyvalientes.api.persistence.entity.InformeClinico;
import org.angelesyvalientes.api.persistence.entity.Persona;
import org.angelesyvalientes.api.persistence.repository.InformeClinicoRepository;
import org.angelesyvalientes.api.persistence.repository.PersonaRepository;
import org.angelesyvalientes.api.security.Res; // Asegúrate de tener esta clase
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile; // Necesitas MultipartFile

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class InformeClinicoService {

    private static final Logger logger = LoggerFactory.getLogger(InformeClinicoService.class);

    private final InformeClinicoRepository informeClinicoRepository;
    private final PersonaRepository personaRepository;
    private final GoogleDriveService googleDriveService; // Asegúrate de inyectar este servicio

    @Autowired
    public InformeClinicoService(InformeClinicoRepository informeClinicoRepository, PersonaRepository personaRepository, GoogleDriveService googleDriveService) {
        this.informeClinicoRepository = informeClinicoRepository;
        this.personaRepository = personaRepository;
        this.googleDriveService = googleDriveService;
        logger.info("InformeClinicoService inicializado.");
    }

    // ... (otros métodos)

    @Transactional
    public InformeClinico createInformeClinico(InformeClinico informeClinico, MultipartFile archivoInforme) {
        logger.info("Iniciando la creación de InformeClinico con datos: {} y archivo: {}", informeClinico, archivoInforme.getOriginalFilename());

        // 1. Validar y obtener la Persona asociada
        if (informeClinico.getPersona() == null || informeClinico.getPersona().getNmIdPersona() == 0) {
            logger.warn("La Persona asociada al informe clínico es obligatoria.");
            throw new IllegalArgumentException("La Persona asociada al informe clínico es obligatoria.");
        }
        Long personaId = (long) informeClinico.getPersona().getNmIdPersona();
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la Persona con ID: " + personaId));
        informeClinico.setPersona(persona);

        String fileId;
        InformeClinico savedInforme = null;
        // 2. Subir el archivo a Google Drive si se proporciona
        if (!archivoInforme.isEmpty()) {
            try {
                // Guardar el archivo temporalmente
                Path tempFile = Files.createTempFile("informe_clinico_", archivoInforme.getOriginalFilename());
                Files.copy(archivoInforme.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
                File fileToUpload = tempFile.toFile();

                // Subir a Google Drive
                Res response = googleDriveService.uploadInformeClinicoPdf(fileToUpload, personaId, null, informeClinico); // El ID del informe es null al crear

                if (response.getStatus() != 200) {
                    logger.error("Error al subir el archivo a Google Drive: {}", response.getMessage());
                    Files.deleteIfExists(tempFile);

                    throw new RuntimeException("Error al subir el archivo a Google Drive: " + response.getMessage());
                }

                fileId = response.getUrl();
                Files.deleteIfExists(tempFile); // Eliminar el archivo temporal

                // 3. Guardar el InformeClinico en la base de datos
                informeClinico.setUrlPdf(fileId); // Establecer la URL del PDF (puede ser null si no se subió o falló)
                savedInforme = informeClinicoRepository.save(informeClinico);
                logger.info("Informe clínico creado con ID: {}", savedInforme.getIdInformeClinico());
            } catch (IOException | GeneralSecurityException e) {
                logger.error("Error al procesar el archivo: {}", e.getMessage());
                throw new RuntimeException("Error al procesar el archivo del informe clínico", e);
            }
        }

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

            Long personaId = (long) informeClinicoActualizado.getPersona().getNmIdPersona();
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
     * Obtiene todas las educaciones asociadas a una persona específica.
     *
     * @param personaId El ID de la persona.
     * @return Una lista de {@link InformeClinico} asociadas a la persona.
     */
    public List<InformeClinico> getInformesClinicosPorPersona(Long personaId) {
        logger.info("Obteniendo informes clínicos para la Persona con ID: {}", personaId);
        return informeClinicoRepository.findByPersona_NmIdPersona(personaId);
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
}