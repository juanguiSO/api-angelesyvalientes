package org.angelesyvalientes.api.service;

import jakarta.transaction.Transactional;
import org.angelesyvalientes.api.persistence.entity.Ficha;
import org.angelesyvalientes.api.persistence.entity.Programa;
import org.angelesyvalientes.api.persistence.repository.FichaRepository;
import org.angelesyvalientes.api.persistence.repository.ProgramaRepository;
import org.angelesyvalientes.api.security.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Service
public class FichaService {

    private static final Logger logger = LoggerFactory.getLogger(FichaService.class);

    private final FichaRepository fichaRepository;
    private final ProgramaRepository programaRepository;
    private final GoogleDriveService googleDriveService;

    @Autowired
    public FichaService(FichaRepository fichaRepository, ProgramaRepository programaRepository, GoogleDriveService googleDriveService) {
        this.fichaRepository = fichaRepository;
        this.programaRepository = programaRepository;
        this.googleDriveService = googleDriveService;
        logger.info("FichaService inicializado.");
    }

    public List<Ficha> obtenerTodas() {
        logger.info("Obteniendo todas las fichas.");
        return fichaRepository.findAll();
    }

    public Optional<Ficha> obtenerPorId(int id) {
        logger.info("Obteniendo ficha por ID: {}", id);
        return fichaRepository.findById(id);
    }

    public void eliminar(int id) {
        logger.info("Eliminando ficha con ID: {}", id);
        Optional<Ficha> fichaExistente = fichaRepository.findById(id);
        if (fichaExistente.isPresent()) {
            // Opcional: Eliminar el PDF de Google Drive al eliminar la ficha de la BD
            String urlRecurso = fichaExistente.get().getUrlRecurso();
            if (urlRecurso != null && !urlRecurso.isEmpty()) {
                try {
                    // Extraer el ID del archivo de la URL
                    String fileId = googleDriveService.extractFileIdFromUrl(urlRecurso);
                    if (fileId != null) {
                        googleDriveService.deleteFile(fileId);
                        logger.info("Archivo PDF '{}' eliminado de Google Drive para la ficha ID: {}", fileId, id);
                    }
                } catch (GeneralSecurityException | IOException e) {
                    logger.error("Error al intentar eliminar el archivo PDF de Google Drive para la ficha ID: {}: {}", id, e.getMessage());
                    // Decide si lanzar una excepción o solo loguear el error y continuar con la eliminación de la BD
                }
            }

            fichaRepository.deleteById(id);
            logger.info("Ficha con ID: {} eliminada exitosamente.", id);
        } else {
            logger.warn("No se encontró Ficha con ID: {} para eliminar.", id);
            throw new RuntimeException("Ficha con ID " + id + " no encontrada para eliminar.");
        }
    }

    // Método para crear/actualizar una Ficha
    @Transactional
    public Ficha guardar(Ficha ficha, MultipartFile archivoRecurso) {
        logger.info("FichaService: Iniciando guardar para ficha con nombre: {}", ficha.getNombre());

        // 1. Validar la existencia del programa
        Programa programa = programaRepository.findById(ficha.getPrograma().getId())
                .orElseThrow(() -> new IllegalArgumentException("Programa con ID " + ficha.getPrograma().getId() + " no encontrado."));

        logger.info("FichaService: Programa encontrado: {}", programa.getNombre());

        // 2. Validar unicidad del código de ficha para el programa
        if (ficha.getCodigo() == null) {
            throw new IllegalArgumentException("El código de la ficha es obligatorio.");
        }

        Optional<Ficha> existingFichaWithSameCode;
        if (ficha.getId() == null) { // Es una nueva ficha
            existingFichaWithSameCode = fichaRepository.findByCodigoAndPrograma(ficha.getCodigo(), programa);
            if (existingFichaWithSameCode.isPresent()) {
                throw new IllegalArgumentException("El código '" + ficha.getCodigo() + "' ya existe para el programa '" + programa.getNombre() + "'.");
            }
        } else { // Es una actualización de ficha existente
            existingFichaWithSameCode = fichaRepository.findByCodigoAndProgramaAndIdIsNot(ficha.getCodigo(), programa, ficha.getId());
            if (existingFichaWithSameCode.isPresent()) {
                throw new IllegalArgumentException("El código '" + ficha.getCodigo() + "' ya existe para otra ficha en el programa '" + programa.getNombre() + "'.");
            }
        }
        logger.info("FichaService: Validación de unicidad de código completada para código: {}", ficha.getCodigo());


        // 3. Manejo de eliminación de PDF anterior (si es una actualización)
        // Solo eliminamos si la ficha ya existe y tiene una URL de recurso
        if (ficha.getId() != null) {
            Optional<Ficha> existingFichaOpt = fichaRepository.findById(ficha.getId());
            existingFichaOpt.ifPresent(f -> {
                // Si la URL del recurso es diferente, o el nombre de archivo (código de ficha) cambia
                // y hay una URL antigua, entonces eliminamos el antiguo archivo de Drive.
                // IMPORTANTE: Si solo el contenido del archivo cambia, el método uploadFichaRecursoPdf
                // ya maneja la sobrescritura por nombre. Esta lógica es para cuando se reemplaza la URL
                // completamente por una nueva (ej. si el código de la ficha cambia y genera un nuevo nombre).
                // Con la lógica de sobrescritura en GoogleDriveService, esta eliminación solo es necesaria
                // si el programa o el código de la ficha cambia Y el archivo antiguo sigue existiendo.
                // Para simplificar, si hay un archivo antiguo y estamos subiendo uno nuevo, asumimos que el antiguo debe irse.
                if (f.getUrlRecurso() != null && !f.getUrlRecurso().isEmpty()) {
                    try {
                        String oldFileId = googleDriveService.extractFileIdFromUrl(f.getUrlRecurso());
                        if (oldFileId != null && !oldFileId.equals(ficha.getUrlRecurso())) { // Solo eliminar si no es el mismo ID
                            googleDriveService.deleteFile(oldFileId);
                            logger.info("FichaService: Archivo anterior de Google Drive eliminado: {}", oldFileId);
                        } else if (oldFileId != null && oldFileId.equals(ficha.getUrlRecurso())) {
                            logger.info("FichaService: La URL del recurso es la misma, no se requiere eliminación. ID: {}", oldFileId);
                        }
                    } catch (GeneralSecurityException | IOException e) {
                        logger.error("FichaService: Error al eliminar archivo anterior de Google Drive: {}", e.getMessage(), e);
                        // Dependiendo de tu política, podrías relanzar o solo loguear.
                        // Para esta operación, un fallo en la eliminación no debería impedir el guardado de la nueva.
                    }
                }
            });
        }
        logger.info("FichaService: Comprobación de eliminación de archivo anterior completada.");


        // Asignar el programa a la ficha antes de guardar
        ficha.setPrograma(programa);

        // Guarda la ficha en la BD (para obtener ID y/o asegurar que exista)
        Ficha fichaGuardada = fichaRepository.save(ficha);
        logger.info("FichaService: Ficha guardada (o actualizada) en BD con ID: {}", fichaGuardada.getId());

        File tempFile = null;
        try {
            // Crea archivo temporal y copia el contenido del PDF
            tempFile = File.createTempFile("temp_ficha_recurso_", ".pdf");
            archivoRecurso.transferTo(tempFile);
            logger.info("FichaService: Archivo temporal creado en: {}", tempFile.getAbsolutePath());

            // Subir archivo a Google Drive
            logger.info("FichaService: Intentando subir archivo a Google Drive para programa: {}", programa.getNombre());
            // Pasar la fichaGuardada para que GoogleDriveService tenga el ID correcto para el nombre del archivo
            // y la URL de la ficha se actualice con el ID de Drive.
            org.angelesyvalientes.api.security.Res driveRes = googleDriveService.uploadFichaRecursoPdf(
                    tempFile,
                    programa.getNombre(),
                    fichaGuardada
            );
            logger.info("FichaService: Resultado de subida a Google Drive: Estatus={}, Mensaje={}", driveRes.getStatus(), driveRes.getMessage());


            if (driveRes.getStatus() == 200) {
                fichaGuardada.setUrlRecurso(driveRes.getUrl());
                fichaRepository.save(fichaGuardada); // Guardar la ficha con la URL de Drive
                logger.info("FichaService: Ficha con ID {} actualizada en BD con nueva URL: {}", fichaGuardada.getId(), fichaGuardada.getUrlRecurso());
            } else {
                logger.error("FichaService: Error reportado por GoogleDriveService: {}", driveRes.getMessage());
                throw new RuntimeException("Error al subir el archivo a Google Drive: " + driveRes.getMessage());
            }

        } catch (IOException | GeneralSecurityException e) {
            logger.error("FichaService: Fallo al procesar el recurso de la ficha (IO/Security): {}", e.getMessage(), e);
            throw new RuntimeException("Fallo al procesar el recurso de la ficha: " + e.getMessage(), e);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
                logger.info("FichaService: Archivo temporal eliminado: {}", tempFile.getAbsolutePath());
            }
        }
        return fichaGuardada;
    }

    // Puedes añadir un método para obtener una ficha por ID si no lo tienes
    public Optional<Ficha> findById(Integer id) {
        return fichaRepository.findById(id);
    }


    /**
     * Obtiene una lista de todas las fichas asociadas a un programa específico.
     *
     * @param programaId El ID del programa.
     * @return Una lista de fichas que pertenecen al programa especificado.
     * @throws IllegalArgumentException si el programa con el ID dado no se encuentra.
     */
    public List<Ficha> obtenerFichasPorPrograma(int programaId) {
        logger.info("Obteniendo fichas para el programa con ID: {}", programaId);
        Programa programa = programaRepository.findById(programaId)
                .orElseThrow(() -> new IllegalArgumentException("Programa con ID " + programaId + " no encontrado."));

        return fichaRepository.findByPrograma(programa);
    }

    public byte[] descargarPdfFicha(int fichaId) {
        logger.info("Intentando descargar PDF para la ficha con ID: {}", fichaId);
        Optional<Ficha> fichaOptional = fichaRepository.findById(fichaId);

        if (fichaOptional.isEmpty()) {
            logger.warn("No se encontró Ficha con ID: {} para descargar PDF.", fichaId);
            throw new IllegalArgumentException("Ficha con ID " + fichaId + " no encontrada.");
        }

        Ficha ficha = fichaOptional.get();
        String fileId = ficha.getUrlRecurso(); // Asumimos que urlRecurso guarda el ID puro de Drive

        if (fileId == null || fileId.isEmpty()) {
            logger.warn("La ficha con ID: {} no tiene un recurso PDF asociado (urlRecurso está vacío).", fichaId);
            throw new IllegalStateException("La ficha no tiene un recurso PDF asociado.");
        }

        try {
            // En GoogleDriveService, creamos el método downloadFile(String fileId)
            byte[] pdfContent = googleDriveService.downloadFile(fileId);
            if (pdfContent == null) {
                logger.error("El archivo PDF con ID {} no pudo ser descargado de Google Drive.", fileId);
                throw new RuntimeException("El archivo PDF no pudo ser descargado del servicio de almacenamiento.");
            }
            logger.info("PDF descargado para la ficha con ID: {}", fichaId);
            return pdfContent;
        } catch (GeneralSecurityException | IOException e) {
            logger.error("Error al descargar el PDF de la ficha con ID {}: {}", fichaId, e.getMessage(), e);
            throw new RuntimeException("Error al acceder al servicio de almacenamiento para descargar el PDF: " + e.getMessage(), e);
        }
    }
}