package org.angelesyvalientes.api.service;

import org.angelesyvalientes.api.persistence.entity.Documentacion;
import org.angelesyvalientes.api.persistence.repository.DocumentacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentacionService {

    private final DocumentacionRepository documentacionRepository;

    @Autowired
    public DocumentacionService(DocumentacionRepository documentacionRepository) {
        this.documentacionRepository = documentacionRepository;
    }

    // Obtener una Documentacion por su ID
    public Optional<Documentacion> getDocumentacion(Long id) {
        return documentacionRepository.findById(id);
    }

    // Obtener todas las Documentaciones
    public List<Documentacion> getAllDocumentaciones() {
        return documentacionRepository.findAll();
    }

    // Guardar una nueva Documentacion
    public Documentacion createDocumentacion(Documentacion documentacion) {
        return documentacionRepository.save(documentacion);
    }

    // Actualizar una Documentacion existente
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

    // Eliminar una Documentacion por su ID
    public void deleteDocumentacion(Long id) {
        Optional<Documentacion> documentacionExistente = documentacionRepository.findById(id);

        if (documentacionExistente.isPresent()) {
            documentacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Documentacion con ID " + id + " no encontrada.");
        }
    }
}